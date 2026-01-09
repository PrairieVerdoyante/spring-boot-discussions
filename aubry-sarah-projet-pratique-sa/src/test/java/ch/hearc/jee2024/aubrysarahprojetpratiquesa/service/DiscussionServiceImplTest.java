package ch.hearc.jee2024.aubrysarahprojetpratiquesa.service;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.DiscussionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DiscussionServiceImplTest {

    private DiscussionRepository repository;
    private DiscussionServiceImpl service;

    @BeforeEach
    void setup() {
        repository = mock(DiscussionRepository.class);
        service = new DiscussionServiceImpl(repository);
    }

    @Test
    void saveDiscussionTest() {
        UserEntity user = new UserEntity("Sarah", "pass123", "ADMIN");
        DiscussionEntity discussion = new DiscussionEntity("Titre", "Contenu", user, "General");

        when(repository.save(discussion)).thenReturn(discussion);

        DiscussionEntity saved = service.save(discussion);

        assertThat(saved).isEqualTo(discussion);
        verify(repository, times(1)).save(discussion);
    }

    @Test
    void findByIdTest() {
        DiscussionEntity discussion = new DiscussionEntity();
        discussion.setTitle("Sujet test");

        when(repository.findById(1L)).thenReturn(Optional.of(discussion));

        Optional<DiscussionEntity> found = service.findById(1L);

        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Sujet test");
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void deleteDiscussionTest() {
        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void findAllTest() {
        DiscussionEntity d1 = new DiscussionEntity();
        DiscussionEntity d2 = new DiscussionEntity();
        List<DiscussionEntity> list = List.of(d1, d2);

        when(repository.findAllWithResponses()).thenReturn(list);

        Iterable<DiscussionEntity> all = service.findAll();

        assertThat(all).hasSize(2);
        verify(repository, times(1)).findAllWithResponses();
    }

    @Test
    void createDiscussionTest() {
        UserEntity user = new UserEntity("Sarah", "pass123", "ADMIN");

        DiscussionEntity discussion = new DiscussionEntity("Titre", "Contenu", user, "General");

        when(repository.save(any(DiscussionEntity.class))).thenReturn(discussion);

        DiscussionEntity created = service.createDiscussion("Titre", "Contenu", "General", user);

        // Vérifier que le repository a bien reçu le bon objet
        ArgumentCaptor<DiscussionEntity> captor = ArgumentCaptor.forClass(DiscussionEntity.class);
        verify(repository).save(captor.capture());
        DiscussionEntity captured = captor.getValue();

        assertThat(captured.getTitle()).isEqualTo("Titre");
        assertThat(captured.getContent()).isEqualTo("Contenu");
        assertThat(captured.getCategory()).isEqualTo("General");
        assertThat(captured.getAuthor()).isEqualTo(user);

        assertThat(created).isEqualTo(discussion);
    }

    @Test
    void findByCategoryTest() {
        UserEntity user = new UserEntity("Sarah", "pass123", "ADMIN");
        DiscussionEntity d1 = new DiscussionEntity("T1", "C1", user, "General");
        DiscussionEntity d2 = new DiscussionEntity("T2", "C2", user, "General");

        List<DiscussionEntity> list = List.of(d1, d2);
        when(repository.findByCategoryAndDeletedFalse("General")).thenReturn(list);

        Iterable<DiscussionEntity> result = service.findByCategory("General");

        assertThat(result).hasSize(2);
        verify(repository, times(1)).findByCategoryAndDeletedFalse("General");
    }
}
