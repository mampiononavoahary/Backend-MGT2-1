package com.mgt2.backendproject.model.entity;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
