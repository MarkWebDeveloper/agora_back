package de.stella.agora_web.user.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.stella.agora_web.user.model.User;

public interface UserRepository extends JpaRepository <User, Long>{
    public User findByUsername(String username);

    public Optional<User> findByUsernameAndPassword(String username, String password);

    public User findByUsernameContaining(String username);


    
}


  

