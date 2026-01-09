package ch.hearc.jee2024.aubrysarahprojetpratiquesa.service;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;

import java.util.Optional;

public interface DiscussionService {
    DiscussionEntity save(DiscussionEntity discussionEntity);
    Optional<DiscussionEntity> findById(Long id);
    Iterable<DiscussionEntity> findAll();
    void delete(Long id);
    Iterable<DiscussionEntity> findByCategory(String category);
}
