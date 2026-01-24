package ch.hearc.jee2024.aubrysarahprojetpratiquesa.service;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.AnswerEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.AnswerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Transactional
    public AnswerEntity addAnswerToDiscussion(DiscussionEntity discussion, String content, UserEntity user) {
        AnswerEntity answer = new AnswerEntity(content, user, discussion);
        discussion.addResponse(answer);
        return answerRepository.save(answer);
    }




}

