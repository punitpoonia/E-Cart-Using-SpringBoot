package com.orderservice.dto;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String username;
    private String password;
    public UserLoginRequest(String username,String password) {}
}
