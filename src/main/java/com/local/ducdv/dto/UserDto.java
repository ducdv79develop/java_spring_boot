package com.local.ducdv.dto;

import com.local.ducdv.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
    @NotEmpty(message = "{validations.user.name.required}")
    @Size(max = 255, message = "{validations.user.name.max}")
    private String name;

    @NotEmpty(message = "{validations.user.email.required}")
    @Email(message = "{validations.user.email.email}")
    @Size(max = 255, message = "{validations.user.email.max}")
    public String email;

    public User toEntity() {
        User user =  new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword("123456");
        return user;
    }
}
