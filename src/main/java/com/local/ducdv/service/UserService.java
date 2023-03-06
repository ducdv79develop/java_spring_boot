package com.local.ducdv.service;

import com.local.ducdv.entity.User;
import com.local.ducdv.model.UserModel;

import java.util.List;

public interface UserService {
    List<UserModel> getUserList();
    User getUserByID(Integer id);
    User storeUser(User user, Integer id);
    Boolean deleteUser(Integer id);
}
