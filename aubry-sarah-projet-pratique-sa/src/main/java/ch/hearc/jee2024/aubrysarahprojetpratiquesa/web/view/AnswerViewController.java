package ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.view;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.AnswerEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.AnswerService;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.DiscussionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnswerViewController {

    private final AnswerService answerService;
    private final DiscussionService discussionService;

    public AnswerViewController(AnswerService answerService,
                                DiscussionService discussionService) {
        this.answerService = answerService;
        this.discussionService = discussionService;
    }

    @PostMapping("/answers/create")
    public String createAnswer(
            @RequestParam Long discussionId,
            @RequestParam String content,
            @AuthenticationPrincipal UserEntity user
    ) {
        if (user == null) {
            return "redirect:/login";
        }

        DiscussionEntity discussion = discussionService.findById(discussionId)
                .orElseThrow();

        AnswerEntity answer = new AnswerEntity(content, user, discussion);
        answerService.save(answer);

        discussion.addResponse(answer);

        return "redirect:/discussions/view";
    }
}
