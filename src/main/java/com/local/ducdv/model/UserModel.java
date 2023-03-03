package com.local.ducdv.model;

import com.local.ducdv.entity.User;

import java.text.SimpleDateFormat;

public class UserModel {
    public Integer id;
    public String name;
    public String email;
    public String password;
    public String birthday;
    public Boolean status;

    public UserModel(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.password = "******";
        if (entity.getBirthday() != null) {
            this.birthday = new SimpleDateFormat("yyyy-MM-dd").format(entity.getBirthday().getTime());
        }
        this.status = entity.getStatus();
    }
}
