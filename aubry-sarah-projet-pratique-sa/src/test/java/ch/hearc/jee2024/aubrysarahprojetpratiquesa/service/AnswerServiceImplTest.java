package ch.hearc.jee2024.aubrysarahprojetpratiquesa.service;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.AnswerEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.AnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AnswerServiceImplTest {

    private AnswerRepository answerRepository;
    private AnswerServiceImpl answerService;

    @BeforeEach
    void setup() {
        answerRepository = mock(AnswerRepository.class);
        answerService = new AnswerServiceImpl(answerRepository);
    }

    @Test
    void saveAnswerTest() {
        UserEntity user = new UserEntity("Sarah", "pass123", "ADMIN");
        DiscussionEntity discussion = new DiscussionEntity("Sujet", "Contenu", user, "General");
        AnswerEntity answer = new AnswerEntity("Ma réponse", user, discussion);

        when(answerRepository.save(answer)).thenReturn(answer);

        AnswerEntity saved = answerService.save(answer);

        // Vérifier que le repository a bien été appelé
        ArgumentCaptor<AnswerEntity> captor = ArgumentCaptor.forClass(AnswerEntity.class);
        verify(answerRepository).save(captor.capture());

        AnswerEntity captured = captor.getValue();
        assertThat(captured.getContent()).isEqualTo("Ma réponse");
        assertThat(captured.getAuthor()).isEqualTo(user);
        assertThat(captured.getDiscussion()).isEqualTo(discussion);

        // Vérifier la valeur retournée
        assertThat(saved).isEqualTo(answer);
    }
}
