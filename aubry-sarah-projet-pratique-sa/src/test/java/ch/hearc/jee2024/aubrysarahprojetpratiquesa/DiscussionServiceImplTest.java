package ch.hearc.jee2024.aubrysarahprojetpratiquesa.service;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.Discussion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
        Discussion discussion = new Discussion(
                "Titre test",
                "Contenu test",
                "Sarah",
                "General"
        );

        Discussion saved = discussionService.save(discussion);

        assertNotNull(saved);
        assertTrue(saved.getId() > 0);
        assertEquals("Titre test", saved.getTitle());
    }

    @Test
    void findByIdTest() {
        Discussion discussion = new Discussion(
                "Sujet",
                "Contenu",
                "Sarah",
                "Test"
        );

        Discussion saved = discussionService.save(discussion);

        Discussion found = discussionService.findById(saved.getId());

        assertNotNull(found);
        assertEquals(saved.getTitle(), found.getTitle());
    }

    @Test
    void findAllTest() {
        discussionService.save(new Discussion("T1", "C1", "A", "Cat1"));
        discussionService.save(new Discussion("T2", "C2", "B", "Cat2"));

        List<Discussion> discussions = discussionService.findAll();

        assertEquals(2, discussions.size());
    }

    @Test
    void deleteDiscussionTest() {
        Discussion discussion = discussionService.save(
                new Discussion("À supprimer", "Contenu", "Sarah", "Temp")
        );

        assertFalse(discussionService.findAll().isEmpty());

        discussionService.delete(discussion.getId());

        assertNull(discussionService.findById(discussion.getId()));
    }
}
