package de.stella.agora_web.posts.model;

import java.time.LocalDateTime;
import java.util.Optional;

import de.stella.agora_web.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Entity(name = "Post")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String message;
    private LocalDateTime creationDate = LocalDateTime.now();
    private String postname;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    public String getPostname() {
        return postname;
    }

    public void setPostname(String postname) {
        this.title = postname;
    }

    public String getContent() {
        return message;
    }

    public void setContent(String content) {
        this.message = content;
    }

    public void setAuthor(Optional<User> user) {
        throw new UnsupportedOperationException("Unimplemented method 'setAuthor'");
    }
}
