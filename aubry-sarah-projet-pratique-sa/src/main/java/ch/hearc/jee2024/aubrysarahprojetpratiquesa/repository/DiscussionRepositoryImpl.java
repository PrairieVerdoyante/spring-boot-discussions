package ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.Discussion;

import java.util.ArrayList;
import java.util.List;

public class DiscussionRepositoryImpl implements DiscussionRepository {

    private final List<Discussion> discussions = new ArrayList<>();
    private final List<String> urls = new ArrayList<>();

    private int currentId = 1;

    public List<Discussion> findAll() {
        return new ArrayList<>(discussions);
    }

    @Override
    public Discussion findById(int id) {
        return discussions.stream()
                .filter(disc -> disc.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Discussion save(Discussion discussion) {
        if (discussion.getId() == 0)
        {
            discussion.setId(currentId++);
        }
        discussions.add(discussion);
        return discussion;
    }

    @Override
    public void delete(int id) {

    }
}
