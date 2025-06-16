package com.orderservice.dto;
import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    public UserDTO() {
    }
    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
