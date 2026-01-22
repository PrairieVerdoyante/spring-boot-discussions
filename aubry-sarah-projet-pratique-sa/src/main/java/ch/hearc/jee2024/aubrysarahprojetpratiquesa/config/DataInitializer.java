package ch.hearc.jee2024.aubrysarahprojetpratiquesa.config;


import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.DiscussionService;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserService userService, DiscussionService discussionService) {
        return args -> {
            // Récupérer ou créer un utilisateur existant
            UserEntity admin = userService.findByUsername("admin")
                    .orElseGet(() -> userService.createUser("admin", "admin123", "ADMIN"));

            // Créer les discussions si elles n'existent pas déjà
            if (!discussionService.findAll().iterator().hasNext()) {
                DiscussionEntity discussion1 = new DiscussionEntity(
                        "Bienvenue sur le forum",
                        "Ceci est la première discussion",
                        admin,
                        "Général"
                );
                discussionService.save(discussion1);

                DiscussionEntity discussion2 = new DiscussionEntity(
                        "Règles du forum",
                        "Merci de lire les règles avant de poster",
                        admin,
                        "Général"
                );
                discussionService.save(discussion2);
            }
        };
    }
}
