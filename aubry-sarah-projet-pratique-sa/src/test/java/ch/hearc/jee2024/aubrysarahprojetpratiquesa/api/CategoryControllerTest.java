package ch.hearc.jee2024.aubrysarahprojetpratiquesa.api;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.CategoryEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.CategoryRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setup() {
        categoryRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createCategory_asAdmin_shouldReturnCreated() throws Exception {
        CategoryEntity category = new CategoryEntity("General");

        String json = objectMapper.writeValueAsString(category);

        String responseJson = mvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("General"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        CategoryEntity saved = objectMapper.readValue(responseJson, CategoryEntity.class);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("General");
        assertThat(categoryRepository.existsById(saved.getId())).isTrue();
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void createCategory_asUser_shouldReturnForbidden() throws Exception {
        CategoryEntity category = new CategoryEntity("Forbidden");

        String json = objectMapper.writeValueAsString(category);

        mvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    void createCategory_unauthenticated_shouldReturn401() throws Exception {
        CategoryEntity category = new CategoryEntity("NoAuth");

        String json = objectMapper.writeValueAsString(category);

        mvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getAllCategories_shouldReturnList() throws Exception {
        categoryRepository.save(new CategoryEntity("Cat1"));
        categoryRepository.save(new CategoryEntity("Cat2"));

        mvc.perform(get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Cat1"))
                .andExpect(jsonPath("$[1].name").value("Cat2"));
    }
}
