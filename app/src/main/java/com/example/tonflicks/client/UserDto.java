package com.example.tonflicks.client;

public class UserDto {
    private long id;
    private String username;
    private String email;
    private String role;

    public int getId() {
        return (int) id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
