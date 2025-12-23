package ch.hearc.jee2024.aubrysarahprojetpratiquesa.service;

import ch.hearc.jee2024.demo.Image;
import ch.hearc.jee2024.demo.repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository repository;

    public ImageServiceImpl(ImageRepository repository) {
        this.repository = repository;
    }

    @Override
    public String sendImage(String message, String destinataire) {
        return "Image successfully sent!";
    }

    @Override
    public String sendImage(Image image) {
        return "send";
    }

    @Override
    public Image save(Image image) {
        return repository.save(image);
    }

    @Override
    public Image findById(int id)
    {
        return repository.findById(id);
    }

    @Override
    public void delete(int id)
    {
        repository.delete(id);
    }

    @Override
    public List<Image> findAll() {
        return repository.findAll();
    }
}
