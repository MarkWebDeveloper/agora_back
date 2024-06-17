package de.stella.agora_web.profiles.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDTO {

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

}