package ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.api;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.CategoryEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
        this.categoryService.save(new CategoryEntity("Informatique"));
        this.categoryService.save(new CategoryEntity("Général"));
    }

    // Create a category
    // only admins!
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryEntity> createCategory(@RequestBody CategoryEntity category) {
        if (categoryService.existsByName(category.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.save(category));
    }

    // every category
    @GetMapping
    public ResponseEntity<Iterable<CategoryEntity>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }
}
