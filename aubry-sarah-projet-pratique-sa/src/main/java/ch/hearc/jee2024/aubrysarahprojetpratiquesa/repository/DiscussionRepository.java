package ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;

@Repository
public interface DiscussionRepository extends CrudRepository<DiscussionEntity,Long> {
}
