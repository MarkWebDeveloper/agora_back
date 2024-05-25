package de.stella.agora_web.user.model;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import de.stella.agora_web.posts.model.Post;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


    @Getter
    @Setter
    
@Entity
@Table (name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column (name = "id_user")
    private Long id;
    private String username;
    private String password;
    private String email;

   
    public User(Object object, @Size(min = 4) String name, @Size(min = 4) String username2, @Size(min = 8) String password2, @Size(min = 8) String repeatedPassword) {
    }

    public User(Long id, String username, String password, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)

    public boolean hasRole(String role) {
        return false;
       
    }

@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
@JsonManagedReference
private Set<Post> posts;

@ManyToMany(fetch = FetchType.LAZY)
@JoinTable(name = "roles_users", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
@JsonManagedReference
private Set<Role> roles;


public GrantedAuthority getRole() {

    return getRole();
   
}

public Collection<? extends GrantedAuthority> getAuthorities() {
 return getAuthorities();
}

public Object getName() {
    return getName();
}




}


