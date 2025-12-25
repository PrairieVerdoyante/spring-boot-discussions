package ch.hearc.jee2024.aubrysarahprojetpratiquesa.web;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.Discussion;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.DiscussionRepositoryImpl;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.DiscussionService;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.service.DiscussionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discussions")
public class DiscussionController {

    private final DiscussionServiceImpl discussionService;

    public DiscussionController(DiscussionServiceImpl service, DiscussionRepositoryImpl DiscussionRepositoryImpl) {
        this.discussionService = service;
    }

    // POST /Discussions : créer une Discussion
    @PostMapping
    public ResponseEntity<Discussion> createDiscussion(@RequestBody Discussion Discussion) {
        Discussion savedDiscussion = discussionService.save(Discussion);
        return new ResponseEntity<>(savedDiscussion, HttpStatus.CREATED);
    }



    // GET /Discussions (lister)
    @GetMapping("")
    public List<Discussion> getAll() {
        return discussionService.findAll();
    }

    // GET /Discussions/{id} (lire le contenu)
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Discussion> getDiscussionById(@PathVariable Integer id) {
        Discussion discussion =  discussionService.findById(id);
        if (discussion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(discussion);

        // TODO: mise a jour des champs
    }

    // UPDATE
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Discussion> updateDiscussion(
            @PathVariable int id,
            @RequestBody Discussion discussion) {

        Discussion existing = discussionService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        existing.setTitle(discussion.getTitle());
        existing.setContent(discussion.getContent());
        existing.setCategory(discussion.getCategory());

        Discussion updated = discussionService.save(existing);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Object> deleteDiscussionById(@PathVariable Integer id) {
        Discussion existing = discussionService.findById(id);

        if (existing == null) {
            return ResponseEntity.notFound().build(); // 404
        }

        discussionService.delete(id);

        // 204 code
        return ResponseEntity.noContent().build();
    }



}
