package com.local.ducdv.service;

import com.local.ducdv.dto.UserDto;
import com.local.ducdv.entity.User;
import com.local.ducdv.exception.AppException;
import com.local.ducdv.model.UserModel;
import com.local.ducdv.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserModel> getUserList() {
        List<User> users = (List<User>) userRepository.findAll();

        List<UserModel> userModels = new ArrayList<>();
        for (User user: users)
            userModels.add(new UserModel(user));

        return userModels;
    }

    public User getUserByID(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public User storeUser(User user, Integer id) {
        try {
            User userSave;
            if (id != null) {
                userSave = userRepository.findById(id).orElse(null);
                if (userSave == null) {
                    throw new AppException(404, "Not Found");
                }
                userSave.setName(user.getName());
                userSave.setEmail(user.getEmail());
            } else {
                userSave = user;
            }

            return userRepository.save(userSave);

        } catch (Exception e) {

            return null;
        }
    }

    public Boolean deleteUser(Integer id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return false;
        }

        userRepository.delete(user);
        return true;
    }
}
