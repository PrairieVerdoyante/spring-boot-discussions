package ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.Discussion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiscussionRepositoryImplTest {

    @Autowired
    private DiscussionRepository discussionRepository;

    @BeforeEach
    void setUp() {
        // Nettoyage du repository avant chaque test
        discussionRepository.findAll()
                .forEach(d -> discussionRepository.delete(d.getId()));
    }

    @Test
    void saveDiscussionTest() {
        Discussion discussion = new Discussion(
                "Titre test",
                "Contenu test",
                "Sarah",
                "General"
        );

        Discussion saved = discussionRepository.save(discussion);

        assertNotNull(saved);
        assertTrue(saved.getId() > 0);
        assertEquals("Titre test", saved.getTitle());
    }

    @Test
    void findByIdTest() {
        Discussion discussion = new Discussion(
                "Sujet",
                "Contenu",
                "Auteur",
                "Test"
        );

        Discussion saved = discussionRepository.save(discussion);

        Discussion found = discussionRepository.findById(saved.getId());

        assertNotNull(found);
        assertEquals(saved.getId(), found.getId());
        assertEquals(saved.getTitle(), found.getTitle());
    }

    @Test
    void findAllTest() {
        discussionRepository.save(new Discussion("T1", "C1", "A1", "Cat1"));
        discussionRepository.save(new Discussion("T2", "C2", "A2", "Cat2"));

        assertEquals(2, discussionRepository.findAll().size());
    }

    @Test
    void deleteTest() {
        Discussion discussion = discussionRepository.save(
                new Discussion("À supprimer", "Contenu", "Auteur", "Temp")
        );

        assertFalse(discussionRepository.findAll().isEmpty());

        discussionRepository.delete(discussion.getId());

        assertTrue(discussionRepository.findAll().isEmpty());
        assertNull(discussionRepository.findById(discussion.getId()));
    }
}
