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

        String passwordEncoded = encoder.encode("bcrypt", encoder.decode("base64", signupDTO.getPassword()));

        User newUser = userRepository.save(new User(null, signupDTO.getUsername(), passwordEncoded, null));

        assignDefaultRole(newUser);

        Profile newProfile = Profile.builder()
                .id(newUser.getId())
                .username(newUser.getUsername())
                .email(newUser.getUsername())
                .build();

        profileRepository.save(newProfile);

        return "User with the username " + newUser.getUsername() + " is successfully created.";

    }

    public void assignDefaultRole(User user) {

        Role defaultRole = roleService.getById(2L);
        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);

        user.setRoles(roles);
    }
}