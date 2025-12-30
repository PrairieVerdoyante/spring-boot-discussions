package ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.api;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.AnswerEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.AnswerRepository;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.DiscussionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/answers")
public class AnswerController {
    // todo: changer repository to service.
    private final DiscussionService discussionService;
    private final AnswerRepository answerRepository;

    public AnswerController(DiscussionService discussionService,
                            AnswerRepository answerRepository) {
        this.discussionService = discussionService;
        this.answerRepository = answerRepository;
    }

    @PostMapping("/{id}/answers")
    public ResponseEntity<?> addAnswer(
            @PathVariable Long id,
            @RequestParam String content,
            @AuthenticationPrincipal UserEntity user
    ) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        DiscussionEntity discussion = discussionService.findById(id)
                .filter(d -> !d.isDeleted() && !d.isBlocked())
                .orElseThrow();

        AnswerEntity answer = new AnswerEntity(content, user, discussion);
        AnswerEntity saved = answerRepository.save(answer);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
