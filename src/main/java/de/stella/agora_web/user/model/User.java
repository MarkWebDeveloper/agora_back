package de.stella.agora_web.user.model;

import java.util.Set;
import java.util.function.BooleanSupplier;

import org.springframework.security.core.GrantedAuthority;

import de.stella.agora_web.profiles.model.Profile;
import de.stella.agora_web.roles.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id; 

    private String username;

    private String password;

    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_users", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public boolean hasRole(String role) {
        return roles.stream().anyMatch(r -> r.getName().equals(role));
    }

    public User() {
    }

    public User(Long id, String username, String password, Set<Role> roles) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
    public User( String userName, String password) { 
        super(); 
          
        this.username = userName; 
        this.password = password; 
    } 
    public GrantedAuthority getAuthority() {
        return null;
    }

    public BooleanSupplier isFavorite() {
        return () -> profile != null && profile.isFavorite();
    }

    public void setFavorite(boolean favorite) {
        if (this.profile == null) {
            this.profile = new Profile(id, email, email, email, email, email, email, email, email, email);
        }
        this.profile.setFavorite(favorite);
    }
}