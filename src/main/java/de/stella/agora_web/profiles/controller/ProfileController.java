package de.stella.agora_web.profiles.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.stella.agora_web.profiles.controller.dto.ProfileDTO;
import de.stella.agora_web.profiles.model.Profile;
import de.stella.agora_web.profiles.service.impl.ProfileServiceImpl;

@RestController
@RequestMapping(path = "${api-endpoint}")
public class ProfileController {
    
    ProfileServiceImpl service;

    public ProfileController(ProfileServiceImpl service) {
        this.service = service;
    }

    @PostAuthorize("returnObject.body.id == authentication.principal.id")
    @GetMapping(path = "/user/profiles/getById/{id}")
    public ResponseEntity<Profile> getById(@NonNull @PathVariable Long id) throws Exception{
        Profile profile = service.getById(id);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(profile);
    }

    @PostAuthorize("returnObject.body.email == authentication.principal.username")
    @GetMapping(path = "/user/profiles/getByEmail/{email}")
    public ResponseEntity<Profile> getByEmail(@NonNull @PathVariable String email)throws Exception{
        Profile profile = service.getByEmail(email);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(profile);
    }

    @PostAuthorize("returnObject.body.id == authentication.principal.id")
    @PutMapping(path = "/user/profiles/{id}")
    public ResponseEntity<Profile> update(@PathVariable Long id, @RequestBody ProfileDTO profileDTO) throws Exception{
        Profile profile = service.update(profileDTO, id);
        return ResponseEntity.accepted().body(profile);
    }

    @PutMapping(path = "/user/profiles/update-favorites/{id}")
    public ResponseEntity<String> addRemoveFavorite(@PathVariable Long id) throws Exception {

        String message = service.updateFavorites(id);

        return ResponseEntity.status(200).body(message);
    }
    
}