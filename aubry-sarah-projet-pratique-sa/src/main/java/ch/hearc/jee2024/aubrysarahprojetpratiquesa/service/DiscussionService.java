package ch.hearc.jee2024.aubrysarahprojetpratiquesa.service;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.Discussion;

import java.util.List;

public interface DiscussionService {
    String sendDiscussion(String message, String destinataire);
    String sendDiscussion(Discussion Discussion);
    Discussion save(Discussion Discussion);
    Discussion findById(int id);
    List<Discussion> findAll();
    void delete(int id);
}
