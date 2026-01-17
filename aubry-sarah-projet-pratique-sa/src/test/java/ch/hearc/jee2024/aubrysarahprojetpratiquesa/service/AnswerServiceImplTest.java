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
    private DiscussionService discussionService;
    private AnswerServiceImpl answerService;

    @BeforeEach
    void setup() {
        answerRepository = mock(AnswerRepository.class);
        discussionService = mock(DiscussionService.class);
        answerService = new AnswerServiceImpl(answerRepository, discussionService);
    }

    @Test
    void saveAnswerTest() {
        UserEntity user = new UserEntity("Sarah", "pass123", "ADMIN");
        DiscussionEntity discussion = new DiscussionEntity("Sujet", "Contenu", user, "General");
        AnswerEntity answer = new AnswerEntity("Ma réponse", user, discussion);

        when(answerRepository.save(answer)).thenReturn(answer);

        AnswerEntity saved = answerService.save(answer);

        ArgumentCaptor<AnswerEntity> captor = ArgumentCaptor.forClass(AnswerEntity.class);
        verify(answerRepository).save(captor.capture());

        AnswerEntity captured = captor.getValue();
        assertThat(captured.getContent()).isEqualTo("Ma réponse");
        assertThat(captured.getUser()).isEqualTo(user);
        assertThat(captured.getDiscussion()).isEqualTo(discussion);

        assertThat(saved).isEqualTo(answer);
    }

    @Test
    void addAnswerToDiscussionTest() {
        UserEntity user = new UserEntity("Sarah", "pass123", "ADMIN");
        DiscussionEntity discussion = new DiscussionEntity("Sujet", "Contenu", user, "General");

        when(answerRepository.save(any(AnswerEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        AnswerEntity saved = answerService.addAnswerToDiscussion(discussion, "Ma réponse", user);

        ArgumentCaptor<AnswerEntity> captor = ArgumentCaptor.forClass(AnswerEntity.class);
        verify(answerRepository).save(captor.capture());

        AnswerEntity captured = captor.getValue();
        assertThat(captured.getContent()).isEqualTo("Ma réponse");
        assertThat(captured.getUser()).isEqualTo(user);
        assertThat(captured.getDiscussion()).isEqualTo(discussion);

        assertThat(discussion.getResponse()).contains(captured);

        assertThat(saved).isEqualTo(captured);
    }
}
