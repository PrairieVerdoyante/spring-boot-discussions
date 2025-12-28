package ch.hearc.jee2024.aubrysarahprojetpratiquesa.web;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.DiscussionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DiscussionViewController {

    private final DiscussionService discussionService;

    public DiscussionViewController(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    // Page pour afficher toutes les discussions
    @GetMapping("/discussions/view")
    public String viewDiscussions(Model model) {
        model.addAttribute("discussions", discussionService.findAll());
        return "discussions"; // discussions.html
    }

    // Page pour afficher le formulaire de création
    @GetMapping("/discussions/new")
    public String showCreateForm() {
        return "discussion-create"; // discussion-create.html
    }

    // POST pour créer une discussion depuis le formulaire
    @PostMapping("/discussions/create")
    public String createDiscussionFromForm(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String category,
            @AuthenticationPrincipal UserEntity user // récupère l'utilisateur connecté
    ) {
        if (user == null) {
            // Redirige vers login si personne n'est connecté
            return "redirect:/login";
        }

        DiscussionEntity discussion = new DiscussionEntity(title, content, user, category);
        discussionService.save(discussion); // utilise la méthode save de ton service

        return "redirect:/discussions/view"; // rafraîchit la page après création
    }
}
