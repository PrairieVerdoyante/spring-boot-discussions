package ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.view;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.DiscussionService;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/discussions")
public class DiscussionViewController {

    private final DiscussionService discussionService;
    private final UserService userService;

    public DiscussionViewController(DiscussionService discussionService, UserService userService) {
        this.discussionService = discussionService;
        this.userService = userService;
    }

    // Page pour afficher toutes les discussions
    @GetMapping({"/view", ""})
    public String viewDiscussions(@RequestParam(required = false) String category,
                                  Model model) {
        if (category == null || category.isBlank()) {
            model.addAttribute("discussions", discussionService.findAll());
        } else {
            model.addAttribute("discussions", discussionService.findByCategory(category));
        }
        model.addAttribute("selectedCategory", category);
        return "discussions"; // discussions.html
    }

    // Page pour afficher le formulaire de création
    @GetMapping("/new")
    public String showCreateForm() {
        return "discussions-create"; // discussion-create.html
    }

    // POST pour créer une discussion depuis le formulaire
    @PostMapping("/create")
    public String createDiscussionFromForm(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String category,
            @AuthenticationPrincipal UserDetails userDetails // récupère utilisateur connecté
    ) {
        if (userDetails == null) {
            // Redirige vers login si personne n'est connecté
            return "redirect:/login";
        }

        UserEntity user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow();

        DiscussionEntity discussion = new DiscussionEntity(title, content, user, category);
        discussionService.save(discussion);

        return "redirect:/discussions"; // rafraîchit la page après création
    }
}
