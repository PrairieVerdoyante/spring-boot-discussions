package ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<CategoryEntity,Long> {
    boolean existsByName(String name);
}
