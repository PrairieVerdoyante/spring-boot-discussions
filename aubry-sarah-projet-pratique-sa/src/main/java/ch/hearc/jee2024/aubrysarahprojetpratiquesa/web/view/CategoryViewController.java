package ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.view;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.CategoryEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryViewController {

    private final CategoryService categoryService;

    public CategoryViewController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Affiche les catégories dans un select pour le filtrage
     * ou dans une page dédiée si besoin
     */
    @GetMapping("/view")
    public String showCategories(
            @RequestParam(value = "selected", required = false) String selectedCategory,
            Model model) {

        Iterable<CategoryEntity> categories = categoryService.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", selectedCategory);

        return "categories"; // Thymeleaf template: categories.html
    }

    @GetMapping("/new")
    public String showNewCategoryForm(Model model) {
        model.addAttribute("category", new CategoryEntity());
        return "category-new";
    }

    @PostMapping("/new")
    public String newCategory(@RequestParam String name) {

        if (!categoryService.existsByName(name)) {
            categoryService.save(new CategoryEntity(name));
        }

        return "redirect:/discussions";
    }



}
