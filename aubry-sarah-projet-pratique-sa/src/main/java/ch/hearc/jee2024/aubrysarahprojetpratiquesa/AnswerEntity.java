package ch.hearc.jee2024.aubrysarahprojetpratiquesa;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "response")
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discussion_id")
    private DiscussionEntity discussion;

    public AnswerEntity() {
        this.createdAt = LocalDateTime.now();
    }

    public AnswerEntity(String content, UserEntity user, DiscussionEntity discussion) {
        this.content = content;
        this.user = user;
        this.discussion = discussion;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public UserEntity getAuthor() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public DiscussionEntity getDiscussion() {
        return discussion;
    }

    public void setDiscussion(DiscussionEntity discussion) {
        this.discussion = discussion;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(UserEntity user) {
        this.user = user;
    }
}
