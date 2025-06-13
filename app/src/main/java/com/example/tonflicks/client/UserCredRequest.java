package com.example.tonflicks.client;

public class UserCredRequest{
    private String password;
    private String email;

    public UserCredRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
