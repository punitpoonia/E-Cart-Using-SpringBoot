package com.userservice.controller;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.userservice.model.User;
import com.userservice.services.UserService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	 @PostMapping("/login")
	    public ResponseEntity<Boolean> loginUser(@RequestBody User user) {
	        boolean isAuthenticated = userService.authenticateUser(user.getUsername(), user.getPassword());
	        return ResponseEntity.ok(isAuthenticated);
	    }

	@GetMapping("/getUser")
	public List<User> fetchUserList(){ 
		List<User> users=new ArrayList<>();
		users =userService.getAllUsers();
		return users;
	}
	@GetMapping("/getUser")
    public String fetchUserListById(@RequestBody User user) {
        return userService.getUserByUsername(username, password);
    }

	@PostMapping("/addUser")
	public User saveUser(@RequestBody @Valid User user) {
		userService.createUser(user);
		return user;
		   
		   
	}
	
	@DeleteMapping("/deleteUser/{username}/{password}")
	public String deleteUserByUsernameAndPassword(@PathVariable String username, @PathVariable String password) { 
	    return userService.deleteUserByUsernameAndPassword(username, password);
	}
	
	@PutMapping("/updateUser/{id}")
	public User saveorUpdateUserList(@RequestBody User user){ 
		return userService.updateUser(user);
	}
}