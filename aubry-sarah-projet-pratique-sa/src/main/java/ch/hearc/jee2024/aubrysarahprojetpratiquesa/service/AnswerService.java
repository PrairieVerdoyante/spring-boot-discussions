package ch.hearc.jee2024.aubrysarahprojetpratiquesa.service;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.AnswerEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;

public interface AnswerService {
    AnswerEntity save(AnswerEntity answer);
    public AnswerEntity addAnswerToDiscussion(Long discussionId, String content, UserEntity user);
}