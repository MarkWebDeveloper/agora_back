package de.stella.agora_web.profiles.service;

import java.util.List;
import java.util.Optional;

import de.stella.agora_web.profiles.controller.dto.ProfileDTO;
import de.stella.agora_web.profiles.model.Profile;



    
public interface IProfileService {

  List<Profile> findAllProfiles();
  Optional<Profile> findProfileById(Long id);
  Profile saveProfile(Profile profile);
  void deleteProfileById(Long id);
  Optional<Profile> findProfileByUsernameAndPassword(String username, String password);
  boolean checkProfileUserRole(String username, String role);
  Object updateProfile(Profile profile, Profile updatedProfile);
  List<Profile> getAllProfiles();
  Profile registerProfile(ProfileDTO profileDTO);
  List<Profile> getProfilesById(List<Long> ids);
  Optional<Profile> findProfileByUsername(String username);
}

