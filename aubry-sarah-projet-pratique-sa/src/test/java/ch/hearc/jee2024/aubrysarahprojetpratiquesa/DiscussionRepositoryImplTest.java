package ch.hearc.jee2024.aubrysarahprojetpratiquesa;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.DiscussionRepository;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DiscussionRepositoryImplTest {

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAndFindById_shouldWork() {
        // GIVEN
        UserEntity user = userRepository.save(
                new UserEntity("Sarah", "ADMIN")
        );

        DiscussionEntity discussion = new DiscussionEntity(
                "Repo test",
                "Contenu",
                user,
                "Category"
        );

        // WHEN
        DiscussionEntity saved = discussionRepository.save(discussion);
        Optional<DiscussionEntity> found =
                discussionRepository.findById(saved.getId());

        // THEN
        assertTrue(found.isPresent());
        assertEquals("Repo test", found.get().getTitle());
        assertEquals("Category", found.get().getCategory());
        assertEquals(user.getId(), found.get().getAuthor().getId());
    }

    @Test
    void deleteById_shouldRemoveDiscussion() {
        // GIVEN
        UserEntity user = userRepository.save(
                new UserEntity("Sarah", "ADMIN")
        );

        DiscussionEntity discussion = discussionRepository.save(
                new DiscussionEntity("Delete", "Contenu", user, "Temp")
        );

        Long id = discussion.getId();

        // WHEN
        discussionRepository.deleteById(id);

        // THEN
        assertTrue(discussionRepository.findById(id).isEmpty());
    }

    @Test
    void softDelete_shouldMarkDiscussionAsDeleted() {
        // GIVEN
        UserEntity user = userRepository.save(
                new UserEntity("Sarah", "ADMIN")
        );

        DiscussionEntity discussion = discussionRepository.save(
                new DiscussionEntity("Soft delete", "Contenu", user, "Test")
        );

        // WHEN
        discussion.setDeleted(true);
        discussionRepository.save(discussion);

        Optional<DiscussionEntity> found =
                discussionRepository.findById(discussion.getId());

        // THEN
        assertTrue(found.isPresent());
        assertTrue(found.get().isDeleted());
    }

    @Test
    void findAll_shouldReturnAllDiscussions() {
        // GIVEN
        UserEntity user = userRepository.save(
                new UserEntity("Sarah", "ADMIN")
        );

        discussionRepository.save(
                new DiscussionEntity("D1", "C1", user, "Cat")
        );
        discussionRepository.save(
                new DiscussionEntity("D2", "C2", user, "Cat")
        );

        // WHEN
        Iterable<DiscussionEntity> all = discussionRepository.findAll();

        // THEN
        long count = StreamSupport.stream(all.spliterator(), false).count();
        assertEquals(2, count);
    }
}
