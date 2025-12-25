package ch.hearc.jee2024.aubrysarahprojetpratiquesa.web;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.DiscussionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/discussions")
public class DiscussionController {

    private final DiscussionServiceImpl discussionService;

    public DiscussionController(DiscussionServiceImpl service) {
        this.discussionService = service;
    }

    // POST /Discussions : créer une Discussion
    @PostMapping
    public ResponseEntity<DiscussionEntity> createDiscussion(@RequestBody DiscussionEntity discussionEntity) {
        DiscussionEntity savedDiscussionEntity = discussionService.save(discussionEntity);
        return new ResponseEntity<>(savedDiscussionEntity, HttpStatus.CREATED);
    }



    // GET /Discussions (lister)
    @GetMapping("")
    public Iterable<DiscussionEntity> getAll() {
        return discussionService.findAll();
    }

    // GET /Discussions/{id} (lire le contenu)
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<DiscussionEntity> getDiscussionById(@PathVariable Long id) {
        Optional<DiscussionEntity> discussion = discussionService.findById(id);
        return discussion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

        // TODO: mise a jour champs.
    }

    // UPDATE
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<DiscussionEntity> updateDiscussion(
            @PathVariable Long id,
            @RequestBody DiscussionEntity discussionEntity) {

        Optional<DiscussionEntity> existingOpt = discussionService.findById(id);

        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        DiscussionEntity existing = existingOpt.get();
        existing.setTitle(discussionEntity.getTitle());
        existing.setContent(discussionEntity.getContent());
        existing.setCategory(discussionEntity.getCategory());

        DiscussionEntity updated = discussionService.save(existing);
        return ResponseEntity.ok(discussionEntity);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Object> deleteDiscussionById(@PathVariable Long id) {
        Optional<DiscussionEntity> existing = discussionService.findById(id);

        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404
        }

        discussionService.delete(id);

        // 204 code
        return ResponseEntity.noContent().build();
    }



}
