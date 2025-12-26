package ch.hearc.jee2024.aubrysarahprojetpratiquesa;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.UserRepository;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.DiscussionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiscussionServiceImplTest {

    @Autowired
    private DiscussionService discussionService;

    @Autowired
    private UserRepository userRepository;

    private UserEntity user;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        user = userRepository.save(new UserEntity("Sarah", "ADMIN"));
    }

    @Test
    void saveAndFindTest() {
        DiscussionEntity discussion = new DiscussionEntity(
                "Service test",
                "Contenu",
                user,
                "General"
        );

        DiscussionEntity saved = discussionService.save(discussion);

        Optional<DiscussionEntity> found =
                discussionService.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Service test", found.get().getTitle());
    }

    @Test
    void deleteTest() {
        DiscussionEntity saved = discussionService.save(
                new DiscussionEntity("À supprimer", "Contenu", user, "Temp")
        );

        discussionService.delete(saved.getId());

        assertTrue(discussionService.findById(saved.getId()).isEmpty());
    }
}
