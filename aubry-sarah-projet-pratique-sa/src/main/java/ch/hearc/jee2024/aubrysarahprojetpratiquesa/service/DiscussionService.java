package ch.hearc.jee2024.aubrysarahprojetpratiquesa.service;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;

import java.util.Optional;

public interface DiscussionService {
    String sendDiscussion(String message, String destinataire);
    String sendDiscussion(DiscussionEntity discussionEntity);
    DiscussionEntity save(DiscussionEntity discussionEntity);
    Optional<DiscussionEntity> findById(Long id);
    Iterable<DiscussionEntity> findAll();
    void delete(Long id);
    DiscussionEntity createDiscussion(String title, String content, String category, UserEntity user);
}
