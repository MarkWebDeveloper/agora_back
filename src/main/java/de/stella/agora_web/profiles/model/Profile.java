package de.stella.agora_web.profiles.model;

import java.util.Set;

import de.stella.agora_web.posts.model.Post;
import de.stella.agora_web.replys.model.Reply;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "profiles")
@Getter
@Setter
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastNameOne;
    private String lastNameTwo;
    private String username;
    private String relationship;
    private String email;
    private String password;
    private String confirmPassword;
    private String city;
    private boolean favorite;



    public Profile() {
    }

    public Profile ( Long id ,String firstName, String lastNameOne, String lastNameTwo, String username, String relationship, String email, String password, String confirmPassword, String city) {
        this.id = id;
        this.firstName = firstName;
        this.lastNameOne = lastNameOne;
        this.lastNameTwo = lastNameTwo;
        this.username = username;
        this.relationship = relationship;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.city = city;
    }
    @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "replies_favorites", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Reply> replys;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "posts_favorites", joinColumns = @JoinColumn(name = "user_id"))
      private Set<Post> posts;
  
    public boolean hasRole(String role) {
        // Add the logic to check if the profile has the specified role
        return false;
    }
}
