package ch.hearc.jee2024.aubrysarahprojetpratiquesa.api;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.AnswerEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.AnswerRepository;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.UserRepository;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.DiscussionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AnswerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiscussionService discussionService;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UserEntity user;
    private DiscussionEntity discussion;

    @BeforeEach
    void setup() {
        // Nettoyer la base
        answerRepository.deleteAll();
        discussionService.findAll().forEach(d -> discussionService.delete(d.getId()));
        userRepository.deleteAll();

        // Créer un utilisateur
        user = new UserEntity("Sarah", "pass123", "ADMIN");
        userRepository.save(user);

        // Créer une discussion
        discussion = discussionService.createDiscussion("Sujet test", "Contenu", "General", user);
    }

    @Test
    @WithMockUser(username = "Sarah", roles = "ADMIN")
    void addAnswerTest() throws Exception {
        String content = "Ceci est une réponse";

        // Appel POST pour ajouter une réponse
        String url = "/api/answers/" + discussion.getId() + "/answers?content=" + content;

        String response = mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Convertir en AnswerEntity
        AnswerEntity saved = objectMapper.readValue(response, AnswerEntity.class);

        // Vérifications
        assertThat(saved).isNotNull();
        assertThat(saved.getContent()).isEqualTo(content);
        assertThat(saved.getAuthor().getUsername()).isEqualTo("Sarah");
        assertThat(saved.getDiscussion().getId()).isEqualTo(discussion.getId());

        // Vérifier que la réponse est bien persistée en base
        var answerOpt = answerRepository.findById(saved.getId());
        assertThat(answerOpt).isPresent();
        assertThat(answerOpt.get().getContent()).isEqualTo(content);
    }
}
