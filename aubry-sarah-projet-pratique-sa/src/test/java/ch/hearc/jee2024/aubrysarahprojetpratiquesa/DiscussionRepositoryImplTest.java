package ch.hearc.jee2024.aubrysarahprojetpratiquesa;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.DiscussionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiscussionRepositoryImplTest {

    @Autowired
    private DiscussionRepository discussionRepository;

    @BeforeEach
    void setUp() {
        // Nettoyage du repository avant chaque test
        discussionRepository.findAll()
                .forEach(d -> discussionRepository.deleteById(d.getId()));
    }

    @Test
    void saveDiscussionTest() {
        DiscussionEntity discussionEntity = new DiscussionEntity(
                "Titre test",
                "Contenu test",
                "Sarah",
                "General"
        );

        DiscussionEntity saved = discussionRepository.save(discussionEntity);

        assertNotNull(saved);
        assertTrue(saved.getId() > 0);
        assertEquals("Titre test", saved.getTitle());
    }

    @Test
    void findByIdTest() {
        DiscussionEntity discussionEntity = new DiscussionEntity(
                "Sujet",
                "Contenu",
                "Auteur",
                "Test"
        );

        DiscussionEntity saved = discussionRepository.save(discussionEntity);

        Optional<DiscussionEntity> found = discussionRepository.findById(saved.getId());

        assertTrue(found.isPresent());

        assertEquals(saved.getId(), found.get().getId());
        assertEquals(saved.getTitle(), found.get().getTitle());
    }

    @Test
    void findAllTest() {
        discussionRepository.save(new DiscussionEntity("T1", "C1", "A1", "Cat1"));
        discussionRepository.save(new DiscussionEntity("T2", "C2", "A2", "Cat2"));

        int count = 0;
        while (discussionRepository.findAll().iterator().hasNext()) {
            count++;
        }
        assertEquals(2, count);

    }

    @Test
    void deleteTest() {
        DiscussionEntity discussionEntity = discussionRepository.save(
                new DiscussionEntity("À supprimer", "Contenu", "Auteur", "Temp")
        );

        assertTrue(discussionRepository.findAll().iterator().hasNext());

        discussionRepository.deleteById(discussionEntity.getId());

        assertFalse(discussionRepository.findAll().iterator().hasNext());

        // l'objet n'existe plus
        Optional<DiscussionEntity> deleted = discussionRepository.findById(discussionEntity.getId());
        assertTrue(deleted.isEmpty());
    }
}
