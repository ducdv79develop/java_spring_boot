package com.local.ducdv.entity;

import java.util.Date;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotEmpty(message = "{validations.user.name.required}")
    @Size(max = 255, message = "{validations.user.name.max}")
    private String name;
    @NotEmpty(message = "{validations.user.email.required}")
    @Email(message = "{validations.user.email.email}")
    @Size(max = 255, message = "{validations.user.email.max}")
    private String email;
    private String password;
    private Date birthday;
    private Boolean status;
}
