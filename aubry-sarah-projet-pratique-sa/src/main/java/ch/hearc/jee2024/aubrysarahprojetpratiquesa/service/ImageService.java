package ch.hearc.jee2024.aubrysarahprojetpratiquesa.service;

import ch.hearc.jee2024.demo.Image;

import java.util.List;

public interface ImageService {
    String sendImage(String message, String destinataire);
    String sendImage(Image image);
    Image save(Image image);
    Image findById(int id);
    List<Image> findAll();
    void delete(int id);
}
