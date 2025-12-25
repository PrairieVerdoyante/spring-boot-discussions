package ch.hearc.jee2024.aubrysarahprojetpratiquesa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
@SpringBootTest
@AutoConfigureMockMvc
class AubrySarahProjetPratiqueSaApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void contextLoads() {
    }

    @Test
    void discussionControllerExists() {
        String controllerFqdn =
                "ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.DiscussionController";

        try {
            Class<?> controller = Class.forName(controllerFqdn);
            boolean isRestController =
                    controller.isAnnotationPresent(RestController.class);

            assertTrue(isRestController,
                    String.format("Controller %s is not a RestController", controllerFqdn));
        } catch (ClassNotFoundException e) {
            fail(String.format("Controller %s does not exist", controllerFqdn));
        }
    }

    @Test
    void createDiscussionShouldReturn201() throws Exception {
        String json = "{\"title\":\"Sujet test\",\"content\":\"Contenu test\",\"category\":\"General\"}";

        mvc.perform(post("/discussions")
                        .contentType(MediaType.APPLICATION_JSON) // type du corps
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Sujet test"));
    }

    @Test
    void getAllDiscussionsShouldReturn200() throws Exception {
        mvc.perform(get("/discussions"))
                .andExpect(status().isOk());
    }

    @Test
    void getDiscussionByIdShouldReturn200() throws Exception {
        String json = """
                {
                  "title": "Sujet unique",
                  "content": "Contenu unique",
                  "category": "Test"
                }
                """;

        String response = mvc.perform(post("/discussions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        mvc.perform(get("/discussions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sujet unique"));
    }

    @Test
    void getDiscussionByIdShouldReturn404IfNotFound() throws Exception {
        mvc.perform(get("/discussions/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteDiscussionShouldReturn204() throws Exception {
        String json = """
                {
                  "title": "Sujet à supprimer",
                  "content": "Contenu",
                  "category": "Temp"
                }
                """;

        mvc.perform(post("/discussions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        mvc.perform(delete("/discussions/1"))
                .andExpect(status().isNoContent());
    }

}
