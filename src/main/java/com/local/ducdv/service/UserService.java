package com.local.ducdv.service;

import com.local.ducdv.entity.User;
import com.local.ducdv.model.UserModel;

import java.util.List;

public interface UserService {
    public List<UserModel> getUserList();
    public User getUserByID(Integer id);
    public User storeUser(User user, Integer id);
    public Boolean deleteUser(Integer id);
}
