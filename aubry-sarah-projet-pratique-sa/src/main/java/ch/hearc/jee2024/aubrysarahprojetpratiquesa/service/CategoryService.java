package ch.hearc.jee2024.aubrysarahprojetpratiquesa.service;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.CategoryEntity;

import java.util.Optional;

public interface CategoryService {

    CategoryEntity save(CategoryEntity category);

    Optional<CategoryEntity> findById(Long id);

    Iterable<CategoryEntity> findAll();

    boolean existsByName(String name);
}
