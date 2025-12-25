package ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository;

import org.springframework.stereotype.Repository;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.Discussion;

import java.util.List;

@Repository
public interface DiscussionRepository {
    List<Discussion> findAll();
    Discussion findById(int id);
    Discussion save(Discussion discussion);
    void delete(int id);
}
