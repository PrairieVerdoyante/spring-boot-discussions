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
