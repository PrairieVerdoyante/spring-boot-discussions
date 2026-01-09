package ch.hearc.jee2024.aubrysarahprojetpratiquesa.api;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.UserRepository;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    void createUserTest() throws Exception {
        // Créer le DTO pour l'utilisateur
        UserDTO dto = new UserDTO();
        dto.setUsername("Alice");
        dto.setPassword("secret123");
        dto.setRole("USER");

        // Convertir en JSON
        String json = objectMapper.writeValueAsString(dto);

        // Appel POST /users
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        // Vérifier que l'utilisateur est bien en base
        var userOpt = userRepository.findByUsername("Alice");
        assertThat(userOpt).isPresent();
        assertThat(userOpt.get().getRole()).isEqualTo("USER");

        // Le mot de passe est encodé, donc différent de "secret123"
        assertThat(userOpt.get().getPassword()).isNotEqualTo("secret123");
    }
}
