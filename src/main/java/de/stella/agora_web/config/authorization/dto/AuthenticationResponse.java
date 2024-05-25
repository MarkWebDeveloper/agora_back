package de.stella.agora_web.config.authorization.dto;



import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}