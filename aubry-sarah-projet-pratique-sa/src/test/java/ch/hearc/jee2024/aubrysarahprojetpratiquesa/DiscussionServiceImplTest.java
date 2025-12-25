package ch.hearc.jee2024.aubrysarahprojetpratiquesa.service;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
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
        DiscussionEntity discussionEntity = new DiscussionEntity(
                "Titre test",
                "Contenu test",
                "Sarah",
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
                "Sarah",
                "Test"
        );

        DiscussionEntity saved = discussionService.save(discussionEntity);

        Optional<DiscussionEntity> found = discussionService.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(saved.getTitle(), found.get().getTitle()); // comparaison d'optional et discussion
    }

    @Test
    void findAllTest() {
        discussionService.save(new DiscussionEntity("T1", "C1", "A", "Cat1"));
        discussionService.save(new DiscussionEntity("T2", "C2", "B", "Cat2"));

        Iterable<DiscussionEntity> discussions = discussionService.findAll();
        long count = StreamSupport.stream(discussions.spliterator(), false)
                .count();
        assertEquals(2, count);
    }

    @Test
    void deleteDiscussionTest() {
        DiscussionEntity discussionEntity = discussionService.save(
                new DiscussionEntity("À supprimer", "Contenu", "Sarah", "Temp")
        );

        Iterable<DiscussionEntity> allDiscussions = discussionService.findAll();
        assertTrue(allDiscussions.iterator().hasNext()); // hasNext de Iterable

        discussionService.delete(discussionEntity.getId());

        Optional<DiscussionEntity> deleted = discussionService.findById(discussionEntity.getId());
        assertTrue(deleted.isEmpty()); // l'objet a bien été supprimé
    }
}
