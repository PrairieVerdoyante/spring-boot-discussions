package ch.hearc.jee2024.aubrysarahprojetpratiquesa.web;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.DiscussionService;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.UserRepository;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.dto.DiscussionCreateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discussions")
public class DiscussionController {

    private final DiscussionService discussionService;
    private final UserRepository userRepository;

    public DiscussionController(DiscussionService discussionService,
                                UserRepository userRepository) {
        this.discussionService = discussionService;
        this.userRepository = userRepository;
    }

    // CREATE DISCUSSION
    @PostMapping
    public ResponseEntity<DiscussionEntity> createDiscussion(
            @RequestBody DiscussionCreateDto dto) {

        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Utilisateur non trouvé")
                );

        DiscussionEntity discussion = new DiscussionEntity(
                dto.getTitle(),
                dto.getContent(),
                user,
                dto.getCategory()
        );

        DiscussionEntity saved = discussionService.save(discussion);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
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
            @RequestBody DiscussionCreateDto dto) {

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
