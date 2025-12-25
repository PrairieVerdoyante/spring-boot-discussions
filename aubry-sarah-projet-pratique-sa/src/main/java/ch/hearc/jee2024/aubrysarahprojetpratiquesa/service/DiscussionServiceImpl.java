package ch.hearc.jee2024.aubrysarahprojetpratiquesa.service;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.Discussion;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.DiscussionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public abstract class DiscussionServiceImpl implements DiscussionService {

    private final DiscussionRepository repository;

    public DiscussionServiceImpl(DiscussionRepository repository) {
        this.repository = repository;
    }

    @Override
    public String sendDiscussion(String message, String destinataire) {
        return "Discussion successfully sent!";
    }

    @Override
    public String sendDiscussion(Discussion discussion) {
        return "send";
    }

    @Override
    public Discussion save(Discussion discussion) {
        return (repository.save(discussion));
    }

    @Override
    public Optional<Discussion> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Long id)
    {
        repository.deleteById(id);
    }

    @Override
    public Iterable<Discussion> findAll() {
        return repository.findAll();
    }
}
