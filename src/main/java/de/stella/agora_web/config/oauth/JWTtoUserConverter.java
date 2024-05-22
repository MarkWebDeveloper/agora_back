package de.stella.agora_web.config.oauth;
import java.util.Collections;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import de.stella.agora_web.user.model.User;

@Component
public class JWTtoUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> { 
  
    @SuppressWarnings({ "unchecked", "null" })
    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt source) { 
         User user = new User(); 
            user.setId(Long.parseLong(source.getSubject())); 
            return new UsernamePasswordAuthenticationToken(user, source, Collections.EMPTY_LIST); 
    } 
      
  
} 