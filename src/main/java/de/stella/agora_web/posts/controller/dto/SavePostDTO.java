package de.stella.agora_web.posts.controller.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SavePostDTO implements Serializable {
    @NotBlank
    private String name;
    private String title;
    private String message;
    private LocalDateTime creationDate;
    private Long userId;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

 

}