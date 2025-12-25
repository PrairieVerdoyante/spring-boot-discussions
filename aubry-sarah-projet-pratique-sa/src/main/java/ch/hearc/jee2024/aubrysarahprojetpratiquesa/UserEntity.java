package ch.hearc.jee2024.aubrysarahprojetpratiquesa;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String role; // Exemple : "USER" / "ADMIN"

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiscussionEntity> discussions = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResponseEntity> responses = new ArrayList<>();

    public UserEntity() {}

    public UserEntity(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
    public List<DiscussionEntity> getDiscussions() { return discussions; }
    public List<ResponseEntity> getResponses() { return responses; }

    public void setUsername(String username) { this.username = username; }
    public void setRole(String role) { this.role = role; }
}
