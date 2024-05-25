package de.stella.agora_web.config.authorization.model;


import java.io.Serializable;
import java.rmi.server.Operation;

import de.stella.agora_web.login.dto.ShowPermissionDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class GrantedPermission implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private ShowPermissionDTO role;

    @SuppressWarnings("deprecation")
    @ManyToOne
    @JoinColumn(name = "operation_id")
    private Operation operation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShowPermissionDTO getRole() {
        return role;
    }

    public void setRole(ShowPermissionDTO role2) {
        this.role = role2;
    }

    @SuppressWarnings("deprecation")
    public Operation getOperation() {
        return operation;
    }

    public void setOperation(@SuppressWarnings("deprecation") de.stella.agora_web.config.authorization.model.Operation operation2) {
        this.operation = operation2;
    }

    public void setOperation(de.stella.agora_web.config.authorization.model.Operation operation2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setOperation'");
    }
}