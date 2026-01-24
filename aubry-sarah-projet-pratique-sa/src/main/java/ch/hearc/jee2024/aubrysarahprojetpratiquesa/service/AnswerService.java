package ch.hearc.jee2024.aubrysarahprojetpratiquesa.service;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.AnswerEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;

import java.util.Optional;

public interface AnswerService {
    AnswerEntity addAnswerToDiscussion(DiscussionEntity discussion,
                                       String content,
                                       UserEntity user);
}