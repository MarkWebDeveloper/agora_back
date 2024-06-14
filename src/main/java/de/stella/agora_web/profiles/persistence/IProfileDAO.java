package de.stella.agora_web.profiles.persistence;

import java.util.List;
import java.util.Optional;

import de.stella.agora_web.profiles.model.Profile;

public interface IProfileDAO {

    List<Profile> findAll();
    Optional<Profile> findById(Long id);
    Optional<Profile> findByUsername(String username);
    Optional<Profile> findByUsernameAndPassword(String username, String password);
    Profile save(Profile profile);
    void deleteById(Long id);
    List<Profile> findById(List<Long> ids);
    Profile update(Profile profile, Profile updatedProfile);
    List<Profile> getAll();
    Profile getLoggedInProfile();
    List<Profile> findAllById(List<Long> ids);
    Optional<Profile> findByEmail(String email);
}



