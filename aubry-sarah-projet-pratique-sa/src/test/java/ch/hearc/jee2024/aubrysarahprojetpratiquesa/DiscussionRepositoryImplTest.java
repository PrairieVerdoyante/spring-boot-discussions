package ch.hearc.jee2024.aubrysarahprojetpratiquesa;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.DiscussionRepository;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DiscussionRepositoryImplTest {

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAndFindTest() {
        UserEntity user = userRepository.save(
                new UserEntity("Sarah", "ADMIN")
        );

        DiscussionEntity discussion = new DiscussionEntity(
                "Repo test",
                "Contenu",
                user,
                "Category"
        );

        DiscussionEntity saved = discussionRepository.save(discussion);

        Optional<DiscussionEntity> found =
                discussionRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Repo test", found.get().getTitle());
    }

    @Test
    void deleteTest() {
        UserEntity user = userRepository.save(
                new UserEntity("Sarah", "ADMIN")
        );

        DiscussionEntity saved = discussionRepository.save(
                new DiscussionEntity("Delete", "Contenu", user, "Temp")
        );

        discussionRepository.deleteById(saved.getId());

        assertTrue(discussionRepository.findById(saved.getId()).isEmpty());
    }
}
