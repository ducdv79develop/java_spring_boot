package com.local.ducdv.service;

import java.util.ArrayList;
import java.util.List;

import com.local.ducdv.exception.AppException;
import com.local.ducdv.mapper.UserMapper;
import com.local.ducdv.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.local.ducdv.entity.User;

@Service
public class UserServiceImpl implements UserService {
//    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserModel> getUserList() {
        List<User> users = userMapper.selectUserXml();
        List<UserModel> userModels = new ArrayList<>();
        for (User user: users) {
            userModels.add(new UserModel(user));
        }
        return userModels;
    }

    public User getUserByID(Integer id) {
        return userMapper.selectUserByIdXml(id).orElse(null);
    }

    public User storeUser(User user, Integer id) {
        try {
            User userSave;
            if (id != null) {
                userSave = userMapper.selectUserById(id).orElse(null);
                if (userSave == null) {
                    throw new AppException(404, "Not Found");
                }
                userSave.setName(user.getName());
                userSave.setEmail(user.getEmail());
                userMapper.updateUserXml(userSave);
            } else {
                userSave = user;
                userMapper.insertUserXml(user);
            }
            return userSave;

        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public Boolean deleteUser(Integer id) {
        User user = userMapper.selectUserById(id).orElse(null);

        if (user == null) {
            return false;
        }

        userMapper.deleteUserXml(user.getId());
        return true;
    }
}
