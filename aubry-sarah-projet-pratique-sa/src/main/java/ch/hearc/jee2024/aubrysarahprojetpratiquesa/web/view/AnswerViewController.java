package ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.view;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.AnswerEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.AnswerService;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.DiscussionService;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnswerViewController {

    private final AnswerService answerService;
    private final DiscussionService discussionService;
    private final UserService userService;

    public AnswerViewController(AnswerService answerService,
                                DiscussionService discussionService, UserService userService) {
        this.answerService = answerService;
        this.discussionService = discussionService;
        this.userService = userService;
    }

    @PostMapping("/answers/create")
    public String createAnswer(
            @RequestParam Long discussionId,
            @RequestParam String content,
            @AuthenticationPrincipal UserDetails userDetails // TODO: userDetails.
    ) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        var user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow();

        DiscussionEntity discussion = discussionService.findById(discussionId)
                .orElseThrow();

        answerService.addAnswerToDiscussion(discussion, content, user);

        return "redirect:/discussions/view";
    }
}
