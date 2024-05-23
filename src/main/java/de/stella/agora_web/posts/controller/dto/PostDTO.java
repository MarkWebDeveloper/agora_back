package de.stella.agora_web.posts.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import de.stella.agora_web.replys.controller.dto.ReplyDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostDTO {
    private String title;
    private String message;
    private LocalDateTime creationDate;
    private Long userId;

    @SuppressWarnings("rawtypes")
    private List<ReplyDTO> replies;
    // private TagDTO course;
    // private UserDTO user;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
