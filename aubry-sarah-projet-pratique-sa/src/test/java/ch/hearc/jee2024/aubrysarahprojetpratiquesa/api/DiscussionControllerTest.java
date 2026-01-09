package ch.hearc.jee2024.aubrysarahprojetpratiquesa.api;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.UserRepository;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.dto.DiscussionCreateDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DiscussionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private final String testUsername = "Sarah";

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        userRepository.save(new UserEntity(testUsername, "pass123", "ADMIN"));
    }

    @Test
    @WithMockUser(username = "Sarah", roles = "ADMIN")
    void createAndGetDiscussionTest() throws Exception {
        // Créer le DTO
        DiscussionCreateDto dto = new DiscussionCreateDto();
        dto.setTitle("Sujet test");
        dto.setContent("Contenu");
        dto.setCategory("General");

        String json = objectMapper.writeValueAsString(dto);

        // CREATE
        MvcResult createResult = mvc.perform(post("/api/discussions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        Long discussionId = objectMapper.readTree(createResult.getResponse().getContentAsString())
                .get("id").asLong();

        // GET BY ID
        mvc.perform(get("/api/discussions/{id}", discussionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sujet test"))
                .andExpect(jsonPath("$.content").value("Contenu"))
                .andExpect(jsonPath("$.category").value("General"));

        // GET ALL
        mvc.perform(get("/api/discussions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Sujet test"));
    }

    @Test
    @WithMockUser(username = "Sarah", roles = "ADMIN")
    void updateAndDeleteDiscussionTest() throws Exception {
        // CREATE d'abord
        DiscussionCreateDto dto = new DiscussionCreateDto();
        dto.setTitle("Sujet à modifier");
        dto.setContent("Contenu");
        dto.setCategory("General");

        String json = objectMapper.writeValueAsString(dto);

        MvcResult createResult = mvc.perform(post("/api/discussions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        Long discussionId = objectMapper.readTree(createResult.getResponse().getContentAsString())
                .get("id").asLong();

        // UPDATE
        DiscussionCreateDto updateDto = new DiscussionCreateDto();
        updateDto.setTitle("Titre modifié");
        updateDto.setContent("Contenu modifié");
        updateDto.setCategory("Updated");

        String updateJson = objectMapper.writeValueAsString(updateDto);

        mvc.perform(put("/api/discussions/{id}", discussionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Titre modifié"))
                .andExpect(jsonPath("$.content").value("Contenu modifié"))
                .andExpect(jsonPath("$.category").value("Updated"));

        // DELETE (soft)
        mvc.perform(delete("/api/discussions/{id}", discussionId))
                .andExpect(status().isNoContent());

        // Vérifier que GET BY ID renvoie 404 (soft delete)
        mvc.perform(get("/api/discussions/{id}", discussionId))
                .andExpect(status().isNotFound());
    }
}
