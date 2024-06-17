package de.stella.agora_web.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.stella.agora_web.user.controller.dto.UserDTO;
import de.stella.agora_web.user.model.User;
import de.stella.agora_web.user.repository.UserRepository;
import de.stella.agora_web.user.services.impl.UserServiceImpl;
import lombok.NonNull;

@RestController
@RequestMapping(path = "${api-endpoint}")
public class UserController {

    private final UserServiceImpl service;
    private final UserRepository userRepository; 

    public UserController(UserServiceImpl service, UserRepository userRepository) { 
        this.service = service;
        this.userRepository = userRepository; 
    }



    @GetMapping("/user/getById/{id}")
    @PreAuthorize("#user.id == #id")
    public ResponseEntity<Object> user(@AuthenticationPrincipal User user, @PathVariable Long id) {
        return ResponseEntity.ok(UserDTO.from(userRepository.findById(id).orElseThrow()));
    }




    @PostMapping(path = "")
    public ResponseEntity<User> create(@NonNull @RequestBody User user) {
        User newUser = service.save(user);
        return ResponseEntity.status(201).body(newUser);
    }
    
}
