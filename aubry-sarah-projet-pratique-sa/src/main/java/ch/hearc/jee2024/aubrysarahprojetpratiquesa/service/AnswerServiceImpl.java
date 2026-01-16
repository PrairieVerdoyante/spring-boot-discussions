package ch.hearc.jee2024.aubrysarahprojetpratiquesa.service;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.AnswerEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.AnswerRepository;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final DiscussionService discussionService;

    public AnswerServiceImpl(AnswerRepository answerRepository, DiscussionService discussionService) {
        this.answerRepository = answerRepository;
        this.discussionService = discussionService;
    }

    @Override
    public AnswerEntity addAnswerToDiscussion(Long discussionId, String content, UserEntity user) {
        DiscussionEntity discussion = discussionService.findById(discussionId)
                .filter(d -> !d.isDeleted() && !d.isBlocked())
                .orElseThrow(() -> new IllegalArgumentException("Discussion not found"));

        AnswerEntity answer = new AnswerEntity(content, user, discussion);
        return answerRepository.save(answer);
    }

    @Override
    public AnswerEntity save(AnswerEntity answer) {
        return answerRepository.save(answer);
    }
}

