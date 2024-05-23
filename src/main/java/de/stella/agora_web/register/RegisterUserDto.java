package de.stella.agora_web.register;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
    private String email;
    private String password;
    private String username;
    private String name;
    private String firstLastName;
    private String secondLastName;
    private String phone;
    private String city;
    private String role;
    public String getUsername() {
        return username;
    }
    public Object getUserName() {
        return username;
    }
      
   
}