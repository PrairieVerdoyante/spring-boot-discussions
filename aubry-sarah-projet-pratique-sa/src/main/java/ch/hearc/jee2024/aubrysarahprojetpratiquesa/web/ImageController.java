package ch.hearc.jee2024.aubrysarahprojetpratiquesa.web;

import ch.hearc.jee2024.demo.Image;
import ch.hearc.jee2024.demo.repository.ImageRepositoryImpl;
import ch.hearc.jee2024.demo.service.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired // TODO
    private final ImageServiceImpl imageService;
    private final ImageRepositoryImpl imageRepositoryImpl;

    public ImageController(ImageServiceImpl service, ImageRepositoryImpl imageRepositoryImpl) {
        this.imageService = service;
        this.imageRepositoryImpl = imageRepositoryImpl;
    }

    // POST /images : créer une image
    @PostMapping
    public ResponseEntity<Image> createImage(@RequestBody Image image) {
        Image savedImage = imageService.save(image);
        return new ResponseEntity<>(savedImage, HttpStatus.CREATED);// retourner id
    }

    // GET /images/all (lister)
    @GetMapping("/all")
    public List<Image> getAll() {
        return imageService.findAll();
    }

    // GET /images/{id} (lire le contenu)
    @GetMapping("/{id}")
    @ResponseBody
    public Image getImageById(@PathVariable Integer id) {
        return imageService.findById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Object> updateImage(@PathVariable Integer id, @RequestBody Image image) {
        Image existing = imageService.findById(id);

        if (existing == null) {
            // 404 NOT FOUND
            return ResponseEntity.notFound().build();
        }

        // Mise à jour des champs autorisés
        existing.setName(image.getName());
        existing.setUrl(image.getUrl());

        Image updated = imageService.save(existing);

        // 200 OK + image mise à jour
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Object> deleteImageById(@PathVariable Integer id) {
        Image existing = imageService.findById(id);

        if (existing == null) {
            return ResponseEntity.notFound().build(); // 404
        }

        imageService.delete(id);

        // 204 code
        return ResponseEntity.noContent().build();
    }



}
