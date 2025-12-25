package ch.hearc.jee2024.aubrysarahprojetpratiquesa.service;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.Discussion;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.DiscussionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussionServiceImpl implements DiscussionService {

    private final DiscussionRepository repository;

    public DiscussionServiceImpl(DiscussionRepository repository) {
        this.repository = repository;
    }

    @Override
    public String sendDiscussion(String message, String destinataire) {
        return "Discussion successfully sent!";
    }

    @Override
    public String sendDiscussion(Discussion Discussion) {
        return "send";
    }

    @Override
    public Discussion save(Discussion Discussion) {
        return repository.save(Discussion);
    }

    @Override
    public Discussion findById(int id)
    {
        return repository.findById(id);
    }

    @Override
    public void delete(int id)
    {
        repository.delete(id);
    }

    @Override
    public List<Discussion> findAll() {
        return repository.findAll();
    }
}
