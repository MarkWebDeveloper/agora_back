package de.stella.agora_web.user.controller.dto;

import java.util.Set;

import de.stella.agora_web.roles.model.Role;
import de.stella.agora_web.user.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
    public class UserDTO {
        private Long id;
        private String name;
        private String username;
        private String password;
        private String role;
        public UserDTO(Long long1, String string, String string2, Set<Role> set) {
        }
        public UserDTO(Long id, String name, String username, String password, String role) {
            this.id = id;
            this.name = name;
            this.username = username;
            this.password = password;
            this.role = role;
        }
        public static UserDTO from(User user) {
            return new UserDTO(
                    user.getId(),
                  
                    user.getUsername(),
                    user.getPassword(),
                    user.getRoles()
            );
        }
        
}