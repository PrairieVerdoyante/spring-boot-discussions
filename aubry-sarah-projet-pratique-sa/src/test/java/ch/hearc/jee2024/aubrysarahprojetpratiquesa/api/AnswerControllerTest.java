package ch.hearc.jee2024.aubrysarahprojetpratiquesa.api;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.AnswerEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.DiscussionRepository;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.UserRepository;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.dto.AnswerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AnswerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    private UserEntity user;
    private DiscussionEntity discussion;

    @BeforeEach
    void setup() {
        // Nettoyer les tables
        discussionRepository.deleteAll();
        userRepository.deleteAll();

        // Créer un utilisateur
        user = new UserEntity("Sarah", "pass123", "ADMIN");
        userRepository.save(user);

        // Créer une discussion
        discussion = new DiscussionEntity("Test Discussion", "Contenu test", user, "General");
        discussionRepository.save(discussion);
    }
    @Test
    @WithMockUser(username = "Sarah", roles = "ADMIN")
    void addAnswer_authenticatedUser_shouldReturnSavedAnswer() throws Exception {
        String content = "Ma réponse test";

        String json = objectMapper.writeValueAsString(java.util.Map.of("content", content));

        String responseJson = mvc.perform(post("/api/answers/{id}", discussion.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.username").value("Sarah"))
                .andExpect(jsonPath("$.discussionId").value(discussion.getId()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        AnswerDto saved = objectMapper.readValue(responseJson, AnswerDto.class);

        assertThat(saved.getContent()).isEqualTo(content);
        assertThat(saved.getUsername()).isEqualTo("Sarah");
        assertThat(saved.getDiscussionId()).isEqualTo(discussion.getId());
    }

    @Test
    void addAnswer_unauthenticatedUser_shouldReturn401() throws Exception {
        String json = objectMapper.writeValueAsString(
                java.util.Map.of("content", "Réponse non autorisée")
        );

        mvc.perform(post("/api/answers/{id}", discussion.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized());
    }
}
