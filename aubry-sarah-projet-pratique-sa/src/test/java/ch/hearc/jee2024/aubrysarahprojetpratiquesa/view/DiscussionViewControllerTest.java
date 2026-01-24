package ch.hearc.jee2024.aubrysarahprojetpratiquesa.view;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.CategoryEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.CategoryService;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.DiscussionService;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.UserService;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.view.DiscussionViewController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DiscussionViewController.class)
class DiscussionViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DiscussionService discussionService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private CategoryService categoryService;


    @Test
    @WithMockUser(roles = "USER")
    void viewDiscussions_shouldReturnPageWithModel() throws Exception {
        List<DiscussionEntity> discussions = List.of(
                new DiscussionEntity("Titre1", "Contenu1", new UserEntity(), "Java"),
                new DiscussionEntity("Titre2", "Contenu2", new UserEntity(), "Spring")
        );

        List<CategoryEntity> categories = List.of(
                new CategoryEntity("Java"),
                new CategoryEntity("Spring")
        );

        when(discussionService.findAll()).thenReturn(discussions);
        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(get("/discussions/view"))
                .andExpect(status().isOk())
                .andExpect(view().name("discussions"))
                .andExpect(model().attributeExists("discussions"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attribute("discussions", discussions))
                .andExpect(model().attribute("categories", categories));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void showCreateForm_shouldReturnFormWithCategories() throws Exception {
        List<CategoryEntity> categories = List.of(new CategoryEntity("Java"));
        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(get("/discussions/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("discussions-create"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attribute("categories", categories));
    }

    /**
     * Creer une discussion
     */
    @Test
    @WithMockUser(username = "sarah")
    void createDiscussionFromForm_shouldSaveDiscussionAndRedirect() throws Exception {
        UserEntity user = new UserEntity();
        user.setUsername("sarah");

        when(userService.findByUsername("sarah")).thenReturn(Optional.of(user));

        mockMvc.perform(post("/discussions/create")
                        .with(csrf())
                        .param("title", "Ma Discussion")
                        .param("content", "Contenu")
                        .param("category", "Java"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/discussions"));

        verify(discussionService).save(any(DiscussionEntity.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteDiscussion_shouldMarkDeletedAndRedirect() throws Exception {
        DiscussionEntity discussion = new DiscussionEntity("Titre", "Contenu", new UserEntity(), "Java");

        when(discussionService.findById(1L)).thenReturn(Optional.of(discussion));

        mockMvc.perform(post("/discussions/delete")
                        .with(csrf())
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/discussions"));

        // Vérifie que deleted = true et sauvegarde
        assert(discussion.isDeleted());
        verify(discussionService).save(discussion);
    }
}
