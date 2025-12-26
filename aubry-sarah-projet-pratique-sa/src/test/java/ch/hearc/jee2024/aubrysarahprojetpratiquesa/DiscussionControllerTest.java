package ch.hearc.jee2024.aubrysarahprojetpratiquesa;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DiscussionControllerTest {

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
                .save(new UserEntity("Sarah", "ADMIN"))
                .getId();
    }

    @Test
    void createAndGetByIdTest() throws Exception {
        String json = """
                {
                  "title": "Sujet test",
                  "content": "Contenu",
                  "category": "General",
                  "userId": %d
                }
                """.formatted(userId);

        MvcResult result = mvc.perform(post("/discussions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode body = objectMapper.readTree(
                result.getResponse().getContentAsString()
        );

        Long discussionId = body.get("id").asLong();

        mvc.perform(get("/discussions/{id}", discussionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sujet test"));
    }

    @Test
    void deleteTest() throws Exception {
        String json = """
        {
          "title": "A supprimer",
          "content": "Contenu",
          "category": "Temp",
          "userId": %d
        }
        """.formatted(userId);

        MvcResult result = mvc.perform(post("/discussions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertFalse(responseBody.isBlank(), "Response body is empty");

        Long id = objectMapper
                .readTree(responseBody)
                .get("id").asLong();

        mvc.perform(delete("/discussions/{id}", id))
                .andExpect(status().isNoContent());
    }
}
