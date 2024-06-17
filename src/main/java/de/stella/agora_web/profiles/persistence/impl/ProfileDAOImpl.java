package de.stella.agora_web.profiles.persistence.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import de.stella.agora_web.profiles.model.Profile;
import de.stella.agora_web.profiles.persistence.IProfileDAO;
import de.stella.agora_web.profiles.repository.ProfileRepository;
import de.stella.agora_web.user.model.User;
@Component
public class ProfileDAOImpl implements IProfileDAO {
    private final ProfileRepository profileRepository;

    public ProfileDAOImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    @Override
    public Optional<Profile> findById(Long id) {
        Objects.requireNonNull(id, "ID cannot be null");
        return profileRepository.findById(id);
    }

    @Override
    public Optional<Profile> findByUsername(String username) {
        return Optional.ofNullable(profileRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Profile not found")));
    }

    @Override
    public Profile save(Profile profile) {
        Objects.requireNonNull(profile, "Profile cannot be null");
        return profileRepository.save(profile);
    }

    @Override
    public void deleteById(Long id) {
        Objects.requireNonNull(id, "ID cannot be null");
        profileRepository.deleteById(id);
    }

    @Override
    public Profile update(Profile profile, Profile updatedProfile) {
        profile.setFirstName(updatedProfile.getFirstName());
        profile.setLastNameOne(updatedProfile.getLastNameOne());
        profile.setLastNameTwo(updatedProfile.getLastNameTwo());
        profile.setUsername(updatedProfile.getUsername());
        profile.setRelationship(updatedProfile.getRelationship());
        profile.setCity(updatedProfile.getCity());
        // Securely update password using a password hashing algorithm
        return profileRepository.save(profile);
    }

    @Override
    public Profile getLoggedInProfile() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication!= null) {
            User user = (User) authentication.getPrincipal();
            Optional<Profile> profile = findByEmail(user.getEmail());
            return profile.orElse(null);
        }
        return null;
    }

    @Override
    public Optional<Profile> findByEmail(String email) {
        return profileRepository.findByEmail(email);
    }

    @Override
    public Optional<Profile> findByUsernameAndPassword(String username, String password) {
        return profileRepository.findByUsernameAndPassword(username, password);
    }
    @Override
    public List<Profile> getAll() {
        return findAll();
    }
    @Override
public List<Profile> findById(List<Long> ids) {
    return profileRepository.findAllById(ids);
}
@Override
public List<Profile> findAllById(List<Long> ids) {
    return profileRepository.findAllById(ids);
}
}