package de.stella.agora_web.replys.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import de.stella.agora_web.user.controller.dto.UserDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReplyDTO<PostDTO> {

    private String title;
    private String message;
    private LocalDateTime creationDate;
    private List<PostDTO> post;
    private UserDTO user;
}