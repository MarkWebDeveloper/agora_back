package de.stella.agora_web.profiles.model;

import java.util.Set;

import de.stella.agora_web.posts.model.Post;
import de.stella.agora_web.replys.model.Reply;
import jakarta.persistence.Column;
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
        @Column(name = "id_profile")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName1;
    private String lastName2;
    private String username;
    private String relationship;
    private String email;
    private String password;
    private String confirmPassword;
    private String city;
    private boolean favorite;





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
    public Profile(Long id, String firstName, String lastName1, String lastName2, String username,
    String relationship, String email, String password, String confirmPassword, String city) {
this.id = id;
this.firstName = firstName;
this.lastName1 = lastName1;
this.lastName2 = lastName2;
this.username = username;
this.relationship = relationship;
this.email = email;
this.password = password;
this.confirmPassword = confirmPassword;
this.city = city;
}
    public static Profile.ProfileBuilder builder() {
        return new Profile.ProfileBuilder();
    }

    public static class ProfileBuilder {
        private Long id;
        private String firstName;
        private String lastName1;
        private String lastName2;
        private String username;
        private String relationship;
        private String email;
        private String password;
        private String confirmPassword;
        private String city;

        public ProfileBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ProfileBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ProfileBuilder lastName1(String lastName1) {
            this.lastName1 = lastName1;
            return this;
        }

        public ProfileBuilder lastName2(String lastName2) {
            this.lastName2 = lastName2;
            return this;
        }

        public ProfileBuilder username(String username) {
            this.username = username;
            return this;
        }

        public ProfileBuilder relationship(String relationship) {
            this.relationship = relationship;
            return this;
        }

        public ProfileBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ProfileBuilder password(String password) {
            this.password = password;
            return this;
        }

        public ProfileBuilder confirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
            return this;
        }

        public ProfileBuilder city(String city) {
            this.city = city;
            return this;
        }

        public Profile build() {
            return new Profile(id, firstName, lastName1, lastName2, username, relationship, email, password, confirmPassword, city);
        }
    }
}
