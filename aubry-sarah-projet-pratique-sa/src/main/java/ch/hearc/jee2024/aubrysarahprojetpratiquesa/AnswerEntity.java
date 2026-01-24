package ch.hearc.jee2024.aubrysarahprojetpratiquesa;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "answer")
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    //@JsonBackReference(value = "user-answer")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discussion_id")
    private DiscussionEntity discussion;

    @Column(nullable = false)
    private LocalDateTime createdAt;

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

    public UserEntity getUser() {
        return user;
    }

    public DiscussionEntity getDiscussion() {
        return discussion;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setDiscussion(DiscussionEntity discussion) {
        this.discussion = discussion;
    }

}
