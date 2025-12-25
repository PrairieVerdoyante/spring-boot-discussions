package ch.hearc.jee2024.aubrysarahprojetpratiquesa.web;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.DiscussionService;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.UserRepository;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.dto.DiscussionCreateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    // POST /discussions
    @PostMapping
    public ResponseEntity<DiscussionEntity> createDiscussion(
            @RequestBody DiscussionCreateDto dto) {

        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        DiscussionEntity discussion = new DiscussionEntity(
                dto.getTitle(),
                dto.getContent(),
                user,
                dto.getCategory()
        );

        DiscussionEntity saved = discussionService.save(discussion);

        // 201 created
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // GET /discussions
    @GetMapping
    public Iterable<DiscussionEntity> getAll() {
        return discussionService.findAll();
    }

    // GET /discussions/{id}
    @GetMapping("/{id}")
    public ResponseEntity<DiscussionEntity> getDiscussionById(@PathVariable Long id) {
        return discussionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /discussions/{id}
    @PutMapping("/{id}")
    public ResponseEntity<DiscussionEntity> updateDiscussion(
            @PathVariable Long id,
            @RequestBody DiscussionEntity updatedData) {

        Optional<DiscussionEntity> existingOpt = discussionService.findById(id);

        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        DiscussionEntity existing = existingOpt.get();
        existing.setTitle(updatedData.getTitle());
        existing.setContent(updatedData.getContent());
        existing.setCategory(updatedData.getCategory());

        DiscussionEntity updated = discussionService.save(existing);
        return ResponseEntity.ok(updated);
    }

    // DELETE /discussions/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscussionById(@PathVariable Long id) {

        if (discussionService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        discussionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
