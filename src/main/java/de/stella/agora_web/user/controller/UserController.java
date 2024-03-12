package de.stella.agora_web.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.stella.agora_web.user.model.User;
import de.stella.agora_web.user.services.IUserService;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@RestController
@RequestMapping(path = "${api-endpoint}/users")
public class UserController {

  @Autowired
  private IUserService userService;

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
      return ResponseEntity.ok(userService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
      return userService.findById(id)
              .map(ResponseEntity::ok)
              .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user) {
      return ResponseEntity.ok(userService.save(user));
  }

  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
      return userService.findById(id)
              .map(user -> {
                 updateUser(user, updatedUser);
                 return ResponseEntity.ok(userService.save(user));
              })
              .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  private Object updateUser(User user, User updatedUser) {
  return userService.update(user, updatedUser == null ? null : updatedUser);
}

@DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
      userService.deleteById(id);
      return ResponseEntity.noContent().build();
  }

 
}