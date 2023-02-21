package com.local.ducdv.dto;

import com.local.ducdv.entity.User;
import jakarta.validation.constraints.*;

public class UserDto {
    @NotEmpty(message = "name is required!")
    @Max(value = 255, message = "name is max 255 characters!")
    public String name;

    @NotEmpty(message = "email is required!")
    @Email
    @Max(value = 255, message = "email is max 255 characters!")
    public String email;

    public User toEntity() {
        User user =  new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword("123456");
        return user;
    }
}
