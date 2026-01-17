package ch.hearc.jee2024.aubrysarahprojetpratiquesa.web.dto;

import java.time.LocalDateTime;

public class AnswerDto {
    private Long id;
    private String content;
    private String username;
    private Long discussionId;
    private LocalDateTime createdAt;

    public AnswerDto(Long id, String content, String username, Long discussionId, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.username = username;
        this.discussionId = discussionId;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getContent() { return content; }
    public String getUsername() { return username; }
    public Long getDiscussionId() { return discussionId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
