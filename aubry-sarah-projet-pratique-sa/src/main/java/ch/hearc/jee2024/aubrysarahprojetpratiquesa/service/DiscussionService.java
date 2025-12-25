package ch.hearc.jee2024.aubrysarahprojetpratiquesa.service;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.Discussion;

import java.util.List;
import java.util.Optional;

public interface DiscussionService {
    String sendDiscussion(String message, String destinataire);
    String sendDiscussion(Discussion discussion);
    Discussion save(Discussion discussion);
    Optional<Discussion> findById(Long id);
    Iterable<Discussion> findAll();
    void delete(Long id);
}
