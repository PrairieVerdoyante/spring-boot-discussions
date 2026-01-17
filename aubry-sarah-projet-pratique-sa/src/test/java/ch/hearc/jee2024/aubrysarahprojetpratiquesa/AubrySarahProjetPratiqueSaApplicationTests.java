package ch.hearc.jee2024.aubrysarahprojetpratiquesa;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
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
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AubrySarahProjetPratiqueSaApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long userId;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        userId = userRepository
                .save(new UserEntity("Sarah", "pass","ADMIN"))
                .getId();
    }

    @Test
    void contextLoads() {
        // vérifie que le contexte Spring démarre
    }

    @Test
    void discussionControllerExists() {
        String controllerFqdn =
                "ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.api.DiscussionController";

        try {
            Class<?> controller = Class.forName(controllerFqdn);
            assertTrue(
                    controller.isAnnotationPresent(RestController.class),
                    "DiscussionController is not annotated with @RestController"
            );
        } catch (ClassNotFoundException e) {
            fail("DiscussionController does not exist");
        }
    }

    @Test
    @WithMockUser(username = "Sarah", roles = "ADMIN")
    void createDiscussionShouldReturn201() throws Exception {
        String json = """
                {
                  "title": "Sujet test",
                  "content": "Contenu test",
                  "category": "General",
                  "userId": %d
                }
                """.formatted(userId);

        mvc.perform(post("/api/discussions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Sujet test"));
    }

    @Test
    @WithMockUser(username = "Sarah", roles = "ADMIN")
    void getAllDiscussionsShouldReturn200() throws Exception {
        mvc.perform(get("/api/discussions"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Sarah", roles = "ADMIN")
    void getDiscussionByIdShouldReturn200() throws Exception {
        String json = """
                {
                  "title": "Sujet unique",
                  "content": "Contenu unique",
                  "category": "Test",
                  "userId": %d
                }
                """.formatted(userId);

        MvcResult result = mvc.perform(post("/api/discussions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode body = objectMapper.readTree(
                result.getResponse().getContentAsString()
        );

        Long discussionId = body.get("id").asLong();

        mvc.perform(get("/api/discussions/{id}", discussionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sujet unique"));
    }

    @Test
    @WithMockUser(username = "Sarah", roles = "ADMIN")
    void getDiscussionByIdShouldReturn404IfNotFound() throws Exception {
        mvc.perform(get("/api/discussions/{id}", 9999))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "Sarah", roles = "ADMIN")
    void deleteDiscussionShouldReturn204() throws Exception {
        String json = """
                {
                  "title": "Sujet à supprimer",
                  "content": "Contenu",
                  "category": "Temp",
                  "userId": %d
                }
                """.formatted(userId);

        MvcResult result = mvc.perform(post("/api/discussions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        Long discussionId = objectMapper
                .readTree(result.getResponse().getContentAsString())
                .get("id").asLong();

        mvc.perform(delete("/api/discussions/{id}", discussionId))
                .andExpect(status().isNoContent());

        mvc.perform(get("/api/discussions/{id}", discussionId))
                .andExpect(status().isNotFound());
    }
}
