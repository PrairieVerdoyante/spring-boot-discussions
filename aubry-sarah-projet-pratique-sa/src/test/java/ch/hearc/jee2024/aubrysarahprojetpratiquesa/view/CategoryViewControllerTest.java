package ch.hearc.jee2024.aubrysarahprojetpratiquesa.view;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.CategoryEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.CategoryService;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.view.CategoryViewController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryViewController.class)
class CategoryViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;

    /**
     * Nouvelle catégorie
     * @throws Exception si un formulaire n'est pas retourné.
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    void showNewCategoryForm_shouldReturnForm() throws Exception {
        mockMvc.perform(get("/categories/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("category-new"))
                .andExpect(model().attributeExists("category"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void newCategory_shouldSaveAndRedirect() throws Exception {
        when(categoryService.existsByName("Test"))
                .thenReturn(false);

        mockMvc.perform(post("/categories/new")
                        .with(csrf())
                        .param("name", "Test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/discussions"));

        verify(categoryService).save(any(CategoryEntity.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void newCategory_whenExists_shouldNotSave() throws Exception {
        when(categoryService.existsByName("Test"))
                .thenReturn(true);

        mockMvc.perform(post("/categories/new")
                        .with(csrf())
                        .param("name", "Test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/discussions"));

        verify(categoryService, never()).save(any());
    }
}
