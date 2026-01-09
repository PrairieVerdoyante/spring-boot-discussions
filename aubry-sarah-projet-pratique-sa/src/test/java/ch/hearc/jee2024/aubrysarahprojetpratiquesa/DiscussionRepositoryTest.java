package ch.hearc.jee2024.aubrysarahprojetpratiquesa;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.DiscussionRepository;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DiscussionRepositoryTest {

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private UserRepository userRepository;

    private UserEntity user;

    @BeforeEach
    void setup() {
        // Nettoyer les tables
        discussionRepository.deleteAll();
        userRepository.deleteAll();

        // Créer un user
        user = new UserEntity("Sarah", "pass123", "ADMIN");
        userRepository.save(user);
    }

    @Test
    void saveAndFindDiscussion() {
        DiscussionEntity d1 = new DiscussionEntity("Titre1", "Contenu1", user, "General");
        DiscussionEntity d2 = new DiscussionEntity("Titre2", "Contenu2", user, "Tech");

        discussionRepository.save(d1);
        discussionRepository.save(d2);

        List<DiscussionEntity> all = discussionRepository.findByDeletedFalse();
        assertThat(all).hasSize(2);

        List<DiscussionEntity> general = discussionRepository.findByCategoryAndDeletedFalse("General");
        assertThat(general).hasSize(1);
        assertThat(general.get(0).getTitle()).isEqualTo("Titre1");
    }

    @Test
    void findAllWithResponsesTest() {
        DiscussionEntity d1 = new DiscussionEntity("Titre1", "Contenu1", user, "General");
        DiscussionEntity d2 = new DiscussionEntity("Titre2", "Contenu2", user, "Tech");

        discussionRepository.save(d1);
        discussionRepository.save(d2);

        List<DiscussionEntity> all = discussionRepository.findAllWithResponses();
        assertThat(all).hasSize(2);
        // Comme il n'y a pas de réponses pour l'instant, juste vérifier qu'on récupère bien les discussions
        assertThat(all).extracting("title").containsExactlyInAnyOrder("Titre1", "Titre2");
    }

    @Test
    void findByDeletedFalseOrderByCreatedAtDescTest() {
        DiscussionEntity d1 = new DiscussionEntity("Titre1", "Contenu1", user, "General");
        DiscussionEntity d2 = new DiscussionEntity("Titre2", "Contenu2", user, "Tech");
        d1.setDeleted(true); // un supprimé
        discussionRepository.save(d1);
        discussionRepository.save(d2);

        List<DiscussionEntity> all = discussionRepository.findByDeletedFalseOrderByCreatedAtDesc();
        assertThat(all).hasSize(1);
        assertThat(all.get(0).getTitle()).isEqualTo("Titre2");
    }
}
