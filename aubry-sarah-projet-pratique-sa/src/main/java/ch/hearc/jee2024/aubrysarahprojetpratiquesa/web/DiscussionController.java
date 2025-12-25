package ch.hearc.jee2024.aubrysarahprojetpratiquesa.web;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.Discussion;
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
    public ResponseEntity<Discussion> createDiscussion(@RequestBody Discussion discussion) {
        Discussion savedDiscussion = discussionService.save(discussion);
        return new ResponseEntity<>(savedDiscussion, HttpStatus.CREATED);
    }



    // GET /Discussions (lister)
    @GetMapping("")
    public Iterable<Discussion> getAll() {
        return discussionService.findAll();
    }

    // GET /Discussions/{id} (lire le contenu)
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Discussion> getDiscussionById(@PathVariable Long id) {
        Optional<Discussion> discussion = discussionService.findById(id);
        return discussion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

        // TODO: mise a jour champs.
    }

    // UPDATE
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Discussion> updateDiscussion(
            @PathVariable Long id,
            @RequestBody Discussion discussion) {

        Optional<Discussion> existingOpt = discussionService.findById(id);

        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Discussion existing = existingOpt.get();
        existing.setTitle(discussion.getTitle());
        existing.setContent(discussion.getContent());
        existing.setCategory(discussion.getCategory());

        Discussion updated = discussionService.save(existing);
        return ResponseEntity.ok(discussion);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Object> deleteDiscussionById(@PathVariable Long id) {
        Optional<Discussion> existing = discussionService.findById(id);

        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404
        }

        discussionService.delete(id);

        // 204 code
        return ResponseEntity.noContent().build();
    }



}
