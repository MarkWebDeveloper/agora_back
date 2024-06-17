package de.stella.agora_web.profiles.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stella.agora_web.profiles.controller.dto.ProfileDTO;
import de.stella.agora_web.profiles.model.Profile;
import de.stella.agora_web.profiles.persistence.IProfileDAO;
import de.stella.agora_web.profiles.service.IProfileService;

@Service
public class ProfileServiceImpl implements IProfileService {

    private final IProfileDAO profileDAO;

    public ProfileServiceImpl(IProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
    }

    @Override
    @Transactional
    public Optional<Profile> findProfileById(Long profileId) {
        return profileDAO.findById(profileId);
    }

    @Override
    @Transactional
    public List<Profile> findAllProfiles() {
        return profileDAO.findAll();
    }

    @Override
    @Transactional
    public Profile saveProfile(Profile profile) {
        return profileDAO.save(profile);
    }

    @Override
    @Transactional
    public void deleteProfileById(Long id) {
        profileDAO.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Profile> findProfileByUsernameAndPassword(String username, String password) {
        return profileDAO.findByUsernameAndPassword(username, password);
    }

    @Override
    @Transactional
    public boolean checkProfileUserRole(String username, String role) {
        return profileDAO.findByUsername(username)
                .map(profile -> profile.hasRole(role))
                .orElse(false);
    }

    @Override
    @Transactional
    public Profile updateProfile(Profile profile, Profile updatedProfile) {
        return profileDAO.update(profile, updatedProfile);
    }

    @Override
    @Transactional
    public List<Profile> getProfilesById(List<Long> ids) {
        return profileDAO.findAllById(ids);
    }

    @Override
    @Transactional
    public Optional<Profile> findProfileByUsername(String username) {
        return profileDAO.findByUsername(username);
    }

    @Transactional
    public Profile getById(Long id) {
        return profileDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    @Transactional
    public Profile getByEmail(String email) {
        return profileDAO.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    @Transactional
    public Profile update(ProfileDTO profileDTO, Long id) {
        Profile existingProfile = getById(id);
        existingProfile.setFirstName(profileDTO.getFirstName());
        existingProfile.setLastNameOne(profileDTO.getLastNameOne());
        existingProfile.setLastNameTwo(profileDTO.getLastNameTwo());
        existingProfile.setUsername(profileDTO.getUsername());
        existingProfile.setRelationship(profileDTO.getRelationship());
        existingProfile.setEmail(profileDTO.getEmail());
        existingProfile.setPassword(profileDTO.getPassword());
        existingProfile.setConfirmPassword(profileDTO.getConfirmPassword());
        existingProfile.setCity(profileDTO.getCity());
        return profileDAO.save(existingProfile);
    }

    @Transactional
    public String updateFavorites(Long id) {
        Profile profile = getById(id);
        profile.setFavorite(!profile.isFavorite());
        Profile updatedProfile = profileDAO.save(profile);
        return updatedProfile.isFavorite() ? "Added to favorites" : "Removed from favorites";
    }

    @Override
    @Transactional
    public Profile registerProfile(ProfileDTO profileDTO) {
        Profile profile = new Profile(
            profileDTO.getId(),
                profileDTO.getFirstName(),
                profileDTO.getLastNameOne(),
                profileDTO.getLastNameTwo(),
                profileDTO.getUsername(),
                profileDTO.getRelationship(),
                profileDTO.getEmail(),
                profileDTO.getPassword(),
                profileDTO.getConfirmPassword(),
                profileDTO.getCity()
        );
        // Hash the password before saving
        profile.setPassword(hashPassword(profileDTO.getPassword()));
        return profileDAO.save(profile);
    }

    private String hashPassword(String password) {
        // Implement password hashing logic here
        return password; // Replace with actual hashed password
    }
    @Override
    public List<Profile> getAllProfiles() {
        return profileDAO.findAll();
    }
}