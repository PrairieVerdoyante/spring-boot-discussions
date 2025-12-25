package ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.Discussion;

import java.util.List;

@Repository
public interface DiscussionRepository extends CrudRepository<Discussion,Long> {
}
