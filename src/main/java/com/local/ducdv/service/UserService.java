package com.local.ducdv.service;

import java.util.List;

import com.local.ducdv.entity.User;
import com.local.ducdv.model.UserModel;

public interface UserService {
    List<UserModel> getUserList();
    User getUserByID(Integer id);
    User storeUser(User user, Integer id);
    Boolean deleteUser(Integer id);
}
