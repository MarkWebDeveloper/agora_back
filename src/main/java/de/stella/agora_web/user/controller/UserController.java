package de.stella.agora_web.user.controller;

import java.nio.file.AccessDeniedException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.stella.agora_web.config.authorization.AuthenticationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService authenticationService;

    @GetMapping("/{username}")
    public ResponseEntity<String> validateUsernameLoggedByUsername(@PathVariable(name = "username")String username) throws AccessDeniedException {
        String rta = "";
        if(Boolean.TRUE.equals(authenticationService.validateAuthLoggedByUsername(username))){
            rta = "The username passed has access, since it is the one that is currently authenticated.";
        }else{
            throw new AccessDeniedException("The username passed has no access, it is NOT the authenticated user.");
        }
        return ResponseEntity.ok(rta);
    }
}