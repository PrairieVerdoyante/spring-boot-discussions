package ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReponseRepository extends CrudRepository<DiscussionEntity,Long> { }
