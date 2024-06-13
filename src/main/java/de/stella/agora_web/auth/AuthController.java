package de.stella.agora_web.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.stella.agora_web.user.model.User;
import de.stella.agora_web.user.services.impl.UserServiceImpl;

@RestController
@RequestMapping(path = "${api-endpoint}")
public class AuthController {
    
    @Autowired
    private UserServiceImpl userService;
    
    @GetMapping(path = "/login")
    public ResponseEntity<Map<String, String>> login() {
        SecurityContext context = SecurityContextHolder.getContext();
        
        Authentication auth = context.getAuthentication();
        
        String username = auth.getName();
        String role = "";
        if (auth.getAuthorities() != null && !auth.getAuthorities().isEmpty()) {
            role = auth.getAuthorities().iterator().next().getAuthority();
        }
        
        // Assuming you can retrieve the user object from the username
        Optional<User> optionalUser = userService.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = optionalUser.get();
        
        Map<String, String> json = new HashMap<>();
        json.put("message", "Logged");
        json.put("username", username);
        json.put("role", role);
        // Assuming the UserDetails object has a method to get the user ID
        json.put("userId", user.getId().toString());
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(json);
    }
}
