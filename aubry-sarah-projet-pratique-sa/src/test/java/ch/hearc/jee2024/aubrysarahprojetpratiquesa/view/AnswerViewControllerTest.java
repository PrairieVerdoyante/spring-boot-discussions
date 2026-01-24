package ch.hearc.jee2024.aubrysarahprojetpratiquesa.view;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.AnswerService;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.DiscussionService;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.UserService;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.view.AnswerViewController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnswerViewController.class)
class AnswerViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AnswerService answerService;

    @MockitoBean
    private DiscussionService discussionService;

    @MockitoBean
    private UserService userService;

    @Test
    @WithMockUser(username = "sarah", roles="ADMIN")
    void createAnswer_shouldSaveAnswerAndRedirect() throws Exception {
        UserEntity user = new UserEntity();
        user.setUsername("sarah");

        DiscussionEntity discussion = new DiscussionEntity();
        discussion.setId(1L);

        when(userService.findByUsername("sarah"))
                .thenReturn(Optional.of(user));

        when(discussionService.findById(1L))
                .thenReturn(Optional.of(discussion));

        mockMvc.perform(post("/answers/create")
                        .with(csrf()) // "injecter" le token à la main
                        .param("discussionId", "1")
                        .param("content", "Ma réponse"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/discussions/view"));

        verify(answerService)
                .addAnswerToDiscussion(discussion, "Ma réponse", user);
    }
}
