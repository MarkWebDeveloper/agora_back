package de.stella.agora_web.auth;



import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import de.stella.agora_web.security.SecurityUser;
import de.stella.agora_web.user.model.User;
import de.stella.agora_web.user.repository.UserRepository;
@Component
public class JWTtoUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> { 

    UserRepository userRepository;
  
    public JWTtoUserConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt source) {
        Long userId = Long.parseLong(source.getSubject());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
        SecurityUser securityUser = new SecurityUser(user);
        return new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
    } 
      
  
} 