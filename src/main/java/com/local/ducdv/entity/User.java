package com.local.ducdv.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "users")
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
}
