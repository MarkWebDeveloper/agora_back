package de.stella.agora_web.user.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.stella.agora_web.user.controller.dto.SaveUserDTO;
import de.stella.agora_web.user.model.User;
import de.stella.agora_web.user.persistence.IUserDAO;
import de.stella.agora_web.user.services.IUserService;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserDAO userDAO;

    
    public UserServiceImpl(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userDAO.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return userDAO.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userDAO.deleteById(id);
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return userDAO.findByUsernameAndPassword(username, password);
    }

    @Override
    public boolean checkUserRole(String username, String role) {
        Optional<User> userOptional = userDAO.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.hasRole(role);
        }
        return false;
    }

    @Override
    public List<User> getAll() {
        return userDAO.findAll();
    }

    @Override
    public User update(User user, User updatedUser) {
        // Assuming 'userDAO' has a method to update a user
        return userDAO.update(user, updatedUser);
    }
    public List<User> getById(List<Long> ids) {
        return (List<User>) userDAO.findById(ids);
    }

    public  Optional<User> findUserById(Long userId) {
        return userDAO.findById(userId);


    }

    public User registerOneCustomer(SaveUserDTO newUser) {
 return userDAO.save(newUser.toUser());
    }
    

}
