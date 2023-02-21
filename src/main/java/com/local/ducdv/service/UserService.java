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

    public UserModel getUserByID(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return new UserModel(user);
        }
        return null;
    }

    public Boolean storeUser(UserDto userDto, Integer id) {
        try {
            User user;
            if (id != null) {
                user = userRepository.findById(id).orElse(null);
                if (user == null) {
                    throw new AppException(404, "Not Found");
                }
                user.setName(userDto.name);
                user.setEmail(userDto.email);
            } else {
                user = userDto.toEntity();
            }
            userRepository.save(user);
            return true;

        } catch (Exception e) {

            return false;
        }
    }
}
