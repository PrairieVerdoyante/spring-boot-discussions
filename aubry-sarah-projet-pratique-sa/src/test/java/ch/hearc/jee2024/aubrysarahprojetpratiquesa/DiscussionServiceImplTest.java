package ch.hearc.jee2024.aubrysarahprojetpratiquesa;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.DiscussionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiscussionServiceImplTest {

    @Autowired
    private DiscussionService discussionService;

    @BeforeEach
    void setUp() {
        // Si ton repository est en mémoire et partagé,
        // il doit être vide au début de chaque test
        discussionService.findAll().forEach(d ->
                discussionService.delete(d.getId())
        );
    }

    @Test
    void saveDiscussionTest() {
        UserEntity userEntity = new UserEntity("Sarah", "admin");
        DiscussionEntity discussionEntity = new DiscussionEntity(
                "Titre test",
                "Contenu test",
                userEntity,
                "General"
        );

        DiscussionEntity saved = discussionService.save(discussionEntity);

        assertNotNull(saved);
        assertTrue(saved.getId() > 0);
        assertEquals("Titre test", saved.getTitle());
    }

    @Test
    void findByIdTest() {
        DiscussionEntity discussionEntity = new DiscussionEntity(
                "Sujet",
                "Contenu",
                new UserEntity(),
                "Test"
        );

        DiscussionEntity saved = discussionService.save(discussionEntity);

        Optional<DiscussionEntity> found = discussionService.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(saved.getTitle(), found.get().getTitle()); // comparaison d'optional et discussion
    }

    @Test
    void findAllTest() { // author
        UserEntity userEntity = new UserEntity("Sarah",  "admin");
        discussionService.save(new DiscussionEntity("T1", "C1", userEntity, "Cat1"));
        discussionService.save(new DiscussionEntity("T2", "C2", userEntity, "Cat2"));

        Iterable<DiscussionEntity> discussions = discussionService.findAll();
        long count = StreamSupport.stream(discussions.spliterator(), false)
                .count();
        assertEquals(2, count);
    }

    @Test
    void deleteDiscussionTest() {
        UserEntity user = new UserEntity("Sarah",  "admin");
        DiscussionEntity discussionEntity = discussionService.save(
                new DiscussionEntity("À supprimer", "Contenu", user, "Temp")
        );

        Iterable<DiscussionEntity> allDiscussions = discussionService.findAll();
        assertTrue(allDiscussions.iterator().hasNext()); // hasNext de Iterable

        discussionService.delete(discussionEntity.getId());

        Optional<DiscussionEntity> deleted = discussionService.findById(discussionEntity.getId());
        assertTrue(deleted.isEmpty()); // l'objet a bien été supprimé
    }
}
