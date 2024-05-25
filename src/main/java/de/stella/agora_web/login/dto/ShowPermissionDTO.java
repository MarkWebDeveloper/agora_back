package de.stella.agora_web.login.dto;


import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ShowPermissionDTO implements Serializable {
    private Long id;
    private String operation;
    private String httpMethod;
    private String module;
    private String role;

}