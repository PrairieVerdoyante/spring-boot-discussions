package ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.api;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.DiscussionService;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.UserService;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.dto.DiscussionCreateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/discussions")
public class DiscussionController {

    private final DiscussionService discussionService;
    private final UserService userService;

    public DiscussionController(DiscussionService discussionService, UserService userService) {
        this.discussionService = discussionService;
        this.userService = userService;
    }

    // CREATE DISCUSSION
    @PostMapping
    public ResponseEntity<DiscussionEntity> createDiscussion(
            @RequestBody DiscussionCreateDto dto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<UserEntity> user = userService.findByUsername(userDetails.getUsername());

        DiscussionEntity discussion = new DiscussionEntity(
                dto.getTitle(),
                dto.getContent(),
                user.get(),
                dto.getCategory()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(discussionService.save(discussion));
    }

    // GET ALL (non supprimées)
    @GetMapping
    public ResponseEntity<Iterable<DiscussionEntity>> getAllDiscussions() {
        return ResponseEntity.ok(discussionService.findAll());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<DiscussionEntity> getDiscussionById(@PathVariable Long id) {
        return discussionService.findById(id)
                .filter(d -> !d.isDeleted())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<DiscussionEntity> updateDiscussion(
            @PathVariable Long id,
            @RequestBody DiscussionCreateDto dto
    ) {
        return discussionService.findById(id)
                .map(existing -> {
                    existing.setTitle(dto.getTitle());
                    existing.setContent(dto.getContent());
                    existing.setCategory(dto.getCategory());
                    return ResponseEntity.ok(discussionService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // SOFT DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDiscussion(@PathVariable Long id) {
        return discussionService.findById(id)
                .map(discussion -> {
                    discussion.setDeleted(true);
                    discussionService.save(discussion);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
