package de.stella.agora_web.user.persistence;

import java.util.List;
import java.util.Optional;

import de.stella.agora_web.user.model.User;

public interface IUserDAO {
    List<User> findAll();
    List<User> findById(List<Long> ids);
    Optional<User> findByUsername(String username);
    User save(User user);
    void deleteById(Long id);
    Optional<User> findByUsernameAndPassword(String username, String password);
    User update(User user, User updatedUser);
  
    Optional<User> findById(Long id);
    User getLoggedInUser();
  
}