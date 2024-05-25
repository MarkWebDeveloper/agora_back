package de.stella.agora_web.roles.dto;


    

    import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
    
    public class SavePermissionDTO implements Serializable {
    
        @NotBlank
        private String role;
        @NotBlank
        private String operation;
    
        public String getRole() {
            return role;
        }
    
        public void setRole(String role) {
            this.role = role;
        }
    
        public String getOperation() {
            return operation;
        }
    
        public void setOperation(String operation) {
            this.operation = operation;
        }
    }