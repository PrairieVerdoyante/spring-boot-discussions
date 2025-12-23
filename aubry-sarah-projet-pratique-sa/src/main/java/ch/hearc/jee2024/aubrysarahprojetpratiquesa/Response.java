package ch.hearc.jee2024.aubrysarahprojetpratiquesa;

import java.time.LocalDateTime;

public class Response {
    private int id;
    private String content;
    private String author;
    private LocalDateTime createdAt;

    public Response() {
        this.createdAt = LocalDateTime.now();
    }

    public Response(String content, String author) {
        this.content = content;
        this.author = author;
        this.createdAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
