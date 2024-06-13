package de.stella.agora_web.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@RestController
@RequestMapping(path = "${api-endpoint}/users")
public class UserController {

     UserServiceImpl service;

    public UserController(UserServiceImpl service) {
        this.service = service;
    }

           @Autowired
        UserRepository userRepository; 
  
        @GetMapping("/user/getById/{id}")
        public ResponseEntity<Object> user(@PathVariable Long id) { 
            User user = service.findById(id).orElseThrow();
            return ResponseEntity.ok(UserDTO.from(user)); 
        }




    @PostMapping(path = "")
    public ResponseEntity<User> create(@NonNull @RequestBody User user) {
        User newUser = service.save(user);
        return ResponseEntity.status(201).body(newUser);
    }
    
}
// @RestController como lo tiene mark revisar que metodo es el mas apropiado
//     @RequestMapping("${api-endpoint}") 
//     public class UserController { 
//         @Autowired
//         UserRepository userRepository; 
  
//         @GetMapping("/user/getById/{id}")
//         @PreAuthorize("#user.id == #id") 
//         public ResponseEntity<UserDTO> user(@AuthenticationPrincipal User user, @PathVariable String id) { 
//             return ResponseEntity.ok(UserDTO.from(userRepository.findById(id).orElseThrow())); 
//         }