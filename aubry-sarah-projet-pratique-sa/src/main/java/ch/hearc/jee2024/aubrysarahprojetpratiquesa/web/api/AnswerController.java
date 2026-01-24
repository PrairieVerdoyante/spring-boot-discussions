package ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.api;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.AnswerEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.AnswerService;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.DiscussionService;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.UserService;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.dto.AnswerCreateDto;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.dto.AnswerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    private final DiscussionService discussionService;
    private final AnswerService answerService;
    private final UserService userService;

    public AnswerController(DiscussionService discussionService,
                            AnswerService answerService,
                            UserService userService) {
        this.discussionService = discussionService;
        this.answerService = answerService;
        this.userService = userService;
    }

    @PostMapping("/{discussionId}")
    public ResponseEntity<AnswerDto> addAnswer(
            @PathVariable Long discussionId,
            @RequestBody java.util.Map<String, String> body,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String content = body.get("content");

        var user = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        var discussion = discussionService.findById(discussionId).orElseThrow();

        var saved = answerService.addAnswerToDiscussion(discussion, content, user);

        // Convertir en DTO
        AnswerDto dto = new AnswerDto(
                saved.getId(),
                saved.getContent(),
                saved.getUser().getUsername(),
                saved.getDiscussion().getId(),
                saved.getCreatedAt()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }


}
