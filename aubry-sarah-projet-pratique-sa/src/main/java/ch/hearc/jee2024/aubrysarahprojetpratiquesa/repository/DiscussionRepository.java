package ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;

import java.util.List;

@Repository
public interface DiscussionRepository extends CrudRepository<DiscussionEntity,Long> {
    @Query("""
    SELECT DISTINCT d
    FROM DiscussionEntity d
    LEFT JOIN FETCH d.response
    WHERE d.deleted = false
""")
    List<DiscussionEntity> findAllWithResponses();

    List<DiscussionEntity> findByDeletedFalse();

    List<DiscussionEntity> findByCategoryAndDeletedFalse(String category);

    List<DiscussionEntity> findByDeletedFalseOrderByCreatedAtDesc();
}
