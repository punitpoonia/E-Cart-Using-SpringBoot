package com.example.demo;

import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.userservice.controller.UserController;
import com.userservice.model.User;
import com.userservice.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceApplicationTests {

	@InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    public UserServiceApplicationTests() {
        MockitoAnnotations.openMocks(this);
    }
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginUser_ShouldReturnTrue_WhenAuthenticated() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        when(userService.authenticateUser("testuser", "password123")).thenReturn(true);
        ResponseEntity<Boolean> response = userController.loginUser(user);
        assertEquals(ResponseEntity.ok(true), response);
    }

    @Test
    void loginUser_ShouldReturnFalse_WhenNotAuthenticated() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("wrongpassword");
        when(userService.authenticateUser("testuser", "wrongpassword")).thenReturn(false);
        ResponseEntity<Boolean> response = userController.loginUser(user);
        assertEquals(ResponseEntity.ok(false), response);
    }
    
    @Test
    void saveUser_ShouldReturnFoundStatus_WhenUserIsCreated() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        when(userService.createUser(user)).thenReturn(user); 
        ResponseEntity<Void> response = userController.saveUser(user);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals("/users/getUser", response.getHeaders().getLocation().toString());
    }
    
    @Test
    void deleteUserByUsernameAndPassword_ShouldReturnSuccessMessage_WhenUserExists() {
        when(userService.deleteUserByUsernameAndPassword("testuser", "password123"))
                .thenReturn("User deleted");
        String response = userController.deleteUserByUsernameAndPassword("testuser", "password123");
        assertEquals("User deleted", response);
    }
    
    @Test
    void saveorUpdateUserList_ShouldReturnUpdatedUser_WhenUserExists() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("newpassword");
        when(userService.updateUser(user)).thenReturn(user);
        User response = userController.saveorUpdateUserList(user);
        assertEquals("testuser", response.getUsername());
        assertEquals("newpassword", response.getPassword());
    }


}
