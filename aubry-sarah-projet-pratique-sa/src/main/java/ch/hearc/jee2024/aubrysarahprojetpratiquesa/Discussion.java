package ch.hearc.jee2024.aubrysarahprojetpratiquesa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Discussion {
    private int id;
    private String title;
    private String content;
    private String author;
    private String category;
    private LocalDateTime createdAt;
    private boolean blocked;
    private boolean deleted;
    private final List<Response> responses = new ArrayList<>();

    public Discussion() {
        this.createdAt = LocalDateTime.now();
    }

    public Discussion(String title, String content, String author, String category) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.createdAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void block() {
        this.blocked = true;
    }

    public void unblock() {
        this.blocked = false;
    }

    public void delete() {
        this.deleted = true;
    }

    public void addResponse(Response response) {
        if (!blocked && !deleted) {
            responses.add(response);
        }
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Discussion)) return false;
        Discussion that = (Discussion) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}