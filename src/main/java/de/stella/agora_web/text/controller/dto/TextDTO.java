package de.stella.agora_web.text.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextDTO {

    private Long id;
    private String title;
    private String description;
    private String image;
    
    public TextDTO() {
    }
    public TextDTO(Long id, String title, String description, String image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
    }
    

  
}