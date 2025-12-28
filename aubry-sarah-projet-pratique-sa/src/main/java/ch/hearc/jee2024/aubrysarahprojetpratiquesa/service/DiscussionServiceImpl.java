package ch.hearc.jee2024.aubrysarahprojetpratiquesa.service;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.DiscussionEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.DiscussionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class DiscussionServiceImpl implements DiscussionService {

    private final DiscussionRepository repository;

    public DiscussionServiceImpl(DiscussionRepository repository) {
        this.repository = repository;
    }

    @Override
    public String sendDiscussion(String message, String destinataire) {
        return "Discussion successfully sent!";
    }

    @Override
    public String sendDiscussion(DiscussionEntity discussionEntity) {
        return "send";
    }

    @Override
    public DiscussionEntity save(DiscussionEntity discussionEntity) {
        return (repository.save(discussionEntity));
    }

    @Override
    public Optional<DiscussionEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Long id)
    {
        repository.deleteById(id);
    }

    @Override
    public Iterable<DiscussionEntity> findAll() {
        return repository.findAll();
    }

    public DiscussionEntity createDiscussion(String title, String content, String category, UserEntity user) {
        DiscussionEntity discussion = new DiscussionEntity(title, content, user, category);
        return repository.save(discussion);
    }
}
