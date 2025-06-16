package com.userservice.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userservice.exception.UserAlreadyExistsException;
import com.userservice.exception.UserNotFoundException;
import com.userservice.model.User;
import com.userservice.repo.UserRepository;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    public boolean authenticateUser(String username, String password) {
    	 if (userRepository.findByUsernameAndPassword(username, password) == null) {
    	throw new UserNotFoundException("User with the provided credentials does not exist.");}
        return true;
    
    }

    public User createUser(User user) {
    	if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("User with username '" + user.getUsername() + "' already exists.");
        }
        return userRepository.save(user);
    }

    public String getUserByUsername(String username,String password) {
    	User user = userRepository.findByUsername(username);
    	User pass=userRepository.findByPassword(password);

        if (user == null || pass == null) {
            throw new UserNotFoundException("Invalid Username or Password");
        }

        return "User Exists";
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
    	if (!userRepository.existsById(user.getId())) {
            throw new UserNotFoundException("User with ID " + user.getId() + " not found.");
        }
        return userRepository.save(user);
    }

    public String deleteUserByUsernameAndPassword(String username, String password) {
        
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user == null) {
            throw new UserNotFoundException("User not found with the given username and password");
        }
        userRepository.delete(user);
        return "User deleted";
    }
}
    