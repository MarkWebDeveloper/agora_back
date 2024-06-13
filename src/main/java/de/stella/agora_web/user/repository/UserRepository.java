package de.stella.agora_web.user.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.stella.agora_web.user.model.User;

//public interface UserRepository extends JpaRepository <User, Long>{
  //  public Optional<User> findByUsername(String username);

    //public Optional<User> findByUsernameAndPassword(String username, String password);

  

    //public User getReferenceById(String string);

      //  Optional<User> findById(String id);
    

    
//}


    
@Repository
public interface UserRepository extends JpaRepository<User, Long>{ 
    boolean existsByUsername(String username); 
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndPassword(String username, String password); 
} 
