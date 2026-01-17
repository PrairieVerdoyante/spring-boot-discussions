package ch.hearc.jee2024.aubrysarahprojetpratiquesa.service;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.CategoryEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public CategoryEntity save(CategoryEntity category) {
        return repository.save(category);
    }

    @Override
    public Optional<CategoryEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<CategoryEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }
}
