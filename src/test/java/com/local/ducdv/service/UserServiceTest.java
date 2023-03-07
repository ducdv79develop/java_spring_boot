package com.local.ducdv.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.local.ducdv.entity.User;
import com.local.ducdv.mapper.UserMapper;
import com.local.ducdv.model.UserModel;

@ExtendWith(MockitoExtension.class)

public class UserServiceTest {

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void whenGetUserList_shouldReturnList() {
        List<User> mockUsers = new ArrayList<>();

        // seeder data user
        for (int i = 0; i < 5; i++) {
            mockUsers.add(new User());
        }

        when(userMapper.selectUserXml()).thenReturn(mockUsers);


        List<UserModel> actualUsers = userService.getUserList();

        assertThat(actualUsers.size()).isEqualTo(mockUsers.size());

        verify(userMapper).selectUserXml();
    }

}
