package de.stella.agora_web.register;



import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import de.stella.agora_web.auth.SignUpDTO;
import de.stella.agora_web.auth.TokenGenerator;
import de.stella.agora_web.encryptations.EncoderFacade;
import de.stella.agora_web.profiles.model.Profile;
import de.stella.agora_web.profiles.repository.ProfileRepository;
import de.stella.agora_web.roles.model.Role;
import de.stella.agora_web.roles.service.RoleService;
import de.stella.agora_web.user.model.User;
import de.stella.agora_web.user.repository.UserRepository;
import lombok.AllArgsConstructor;



@Service
@AllArgsConstructor
public class RegisterService {

    UserRepository userRepository;
    RoleService roleService;
    EncoderFacade encoder;
    ProfileRepository profileRepository;
    TokenGenerator tokenGenerator;

    public String createUser(SignUpDTO signupDTO) {

        User newUser = new User(signupDTO.getUsername(), signupDTO.getPassword()); 

        System.out.println(newUser.getId());

        String passwordDecoded = encoder.decode("base64", newUser.getPassword());
        String passwordEncoded = encoder.encode("bcrypt", passwordDecoded);
        
        newUser.setPassword(passwordEncoded);
        assignDefaultRole(newUser);

        userRepository.save(newUser);

        User savedUser = userRepository.getReferenceById(newUser.getId());

        Profile newProfile = new Profile(null, passwordEncoded, passwordEncoded, passwordEncoded, passwordEncoded, passwordEncoded, passwordEncoded, passwordEncoded, passwordEncoded, passwordEncoded);
        
        

        profileRepository.save(newProfile);

        String message = "User with the username " + newUser.getUsername() + " is successfully created.";

        return message;

    }

    public void assignDefaultRole(User user) {

        Role defaultRole = roleService.getById(2L);
        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);

        user.setRoles(roles);
    }
}