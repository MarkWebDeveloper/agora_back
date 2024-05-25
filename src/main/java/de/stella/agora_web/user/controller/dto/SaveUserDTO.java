package de.stella.agora_web.user.controller.dto;
import java.io.Serializable;

import org.springframework.stereotype.Service;

import de.stella.agora_web.user.model.User;
import jakarta.validation.constraints.Size;
    
    @Service
    public class SaveUserDTO  implements Serializable {
        @Size(min = 4)
        private String name;
        @Size(min = 4)
        private String username;
        @Size(min = 8)
        private String password;
        @Size(min = 8)
        private String repeatedPassword;
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public String getUsername() {
            return username;
        }
    
        public void setUsername(String username) {
            this.username = username;
        }
    
        public String getPassword() {
            return password;
        }
    
        public void setPassword(String password) {
            this.password = password;
        }
    
        public String getRepeatedPassword() {
            return repeatedPassword;
        }
    
        public void setRepeatedPassword(String repeatedPassword) {
            this.repeatedPassword = repeatedPassword;
        }

        public User toUser() {
          return new User(null, name, username, password, repeatedPassword);
          
        }
    }