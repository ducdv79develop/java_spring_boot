package com.local.ducdv.mapper;

import com.local.ducdv.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    String GET_USER = "SELECT * FROM users;";
    @Select(GET_USER)
    List<User> selectUser();
    List<User> selectUserXml();

    String GET_USER_BY_ID = "SELECT * FROM users WHERE ID = #{id}";
    @Select(GET_USER_BY_ID)
    Optional<User> selectUserById(Integer id);
    Optional<User> selectUserByIdXml(Integer id);

    String INSERT_USER = "INSERT INTO users (email, name, password, birthday, status) VALUES (#{email}, #{name}, #{password}, #{birthday}, #{status})";
    @Insert(INSERT_USER)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Optional<User> insertUser(User user);
    void insertUserXml(User user);

    String UPDATE_USER = "UPDATE users SET email = #{email}, name = #{name}, password = #{password}, birthday = #{birthday}, status = #{status} WHERE id = #{id}";
    @Update(UPDATE_USER)
    Optional<User> updateUser(User user);
    void updateUserXml(User user);

    String DELETE_USER = "DELETE FROM users WHERE users.id = #{id}";
    @Delete(DELETE_USER)
    void deleteUser(Integer id);
    void deleteUserXml(Integer id);
}
