package de.stella.agora_web.user.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.stella.agora_web.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{ 
    boolean existsByUsername(String username); 
    Optional<User> findByUsername(String username);
    void deleteById(Long id);
    Optional<User> findByUsernameAndPassword(String username, String password);
    List<User> findAllById(Iterable<Long> ids);
    @SuppressWarnings("null")
    Optional<User> findById(Long id);
} 