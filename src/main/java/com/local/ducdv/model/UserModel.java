package com.local.ducdv.model;

import com.local.ducdv.entity.User;

public class UserModel {
    public Integer id;
    public String name;
    public String email;
    public String password;

    public UserModel(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
//        this.password = entity.getPassword();
        this.password = "******";
    }
}
