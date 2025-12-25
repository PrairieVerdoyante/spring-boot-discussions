package ch.hearc.jee2024.aubrysarahprojetpratiquesa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DiscussionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void createDiscussionTest() throws Exception {
        String json = "{\"title\":\"Sujet test\",\"content\":\"Contenu test\",\"author\":\"Sarah\",\"category\":\"General\"}";

        mvc.perform(post("/discussions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Sujet test"))
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    void getAllDiscussionsTest() throws Exception {
        mvc.perform(get("/discussions"))
                .andExpect(status().isOk());
    }

    @Test
    void getDiscussionByIdTest() throws Exception {
        String json = "{\"title\":\"Sujet unique\",\"content\":\"Contenu\",\"author\":\"Sarah\",\"category\":\"Test\"}";

        mvc.perform(post("/discussions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        mvc.perform(get("/discussions/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sujet unique"));
    }

    @Test
    void getDiscussionByIdNotFoundTest() throws Exception {
        mvc.perform(get("/discussions/{id}", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateDiscussionTest() throws Exception {
        String createJson = "{\"title\":\"Avant\",\"content\":\"Contenu\",\"author\":\"Sarah\",\"category\":\"Test\"}";
        String updateJson = "{\"title\":\"Après\",\"content\":\"Nouveau contenu\",\"category\":\"Update\"}";

        mvc.perform(post("/discussions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isCreated());

        mvc.perform(put("/discussions/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Après"));
    }

    @Test
    void deleteDiscussionTest() throws Exception {
        String json = "{\"title\":\"À supprimer\",\"content\":\"Contenu\",\"author\":\"Sarah\",\"category\":\"Temp\"}";

        mvc.perform(post("/discussions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        mvc.perform(delete("/discussions/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
