package com.local.ducdv.api;

import java.util.List;

import com.local.ducdv.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import com.local.ducdv.dto.ApiResponseDto;
import com.local.ducdv.dto.ApiResponseValidationDto;
import com.local.ducdv.entity.User;
import com.local.ducdv.model.UserModel;
import com.local.ducdv.service.UserService;
import com.local.ducdv.util.ResponseStatusCode;
import com.local.ducdv.util.ValidationUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserApiController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping(path="/users")
    public @ResponseBody ResponseEntity<?> getAllUsers() {
        List<UserModel> userLists = userService.getUserList();
        Object[] result = userLists.toArray();
        ApiResponseDto apiResponseDto = new ApiResponseDto(ResponseStatusCode.OK, "success", result);

        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }
    @GetMapping(path="/users/{id}")
    public @ResponseBody ResponseEntity<?> findById(@PathVariable Integer id) {
        User user = userService.getUserByID(id);
        Object[] result = {user};
        ApiResponseDto apiResponseDto = new ApiResponseDto(ResponseStatusCode.OK, "success", result);

        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    @PostMapping("/user/create")
    public ResponseEntity<?> create(@RequestBody @Valid User user, BindingResult result){
        if (result.hasErrors()) {
            ApiResponseValidationDto apiResponseDto = new ApiResponseValidationDto(ValidationUtils.getErrorMessages(result).toArray());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            User userSave = userService.storeUser(user, null);
            Object[] data = {userSave};
            ApiResponseDto apiResponseDto = new ApiResponseDto(ResponseStatusCode.OK, "success", data);
            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
        }
    }

    @PutMapping("/user/update")
    public ResponseEntity<?> update(@RequestBody @Valid User user, BindingResult result) {

        if (user.getId() == null) {
            result.addError(new ObjectError("id", "id is required"));
            ApiResponseValidationDto apiResponseDto = new ApiResponseValidationDto(ValidationUtils.getErrorMessages(result).toArray());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (result.hasErrors()) {
            ApiResponseValidationDto apiResponseDto = new ApiResponseValidationDto(ValidationUtils.getErrorMessages(result).toArray());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            User userByID = userService.getUserByID(user.getId());

            if (userByID == null) {
                Object[] data = {};
                ApiResponseDto apiResponseDto = new ApiResponseDto(ResponseStatusCode.NOT_FOUND, "id not found", data);
                return new ResponseEntity<>(apiResponseDto, HttpStatus.NOT_FOUND);
            }

            User userSave = userService.storeUser(user, user.getId());
            Object[] data = {userSave};
            ApiResponseDto apiResponseDto = new ApiResponseDto(ResponseStatusCode.OK, "success", data);
            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
        }
    }

    @DeleteMapping("user/delete")
    public ResponseEntity<?> delete(@RequestBody User user, BindingResult result){
        if (user.getId() == null) {
            result.addError(new ObjectError("id", "id is required"));
            ApiResponseValidationDto apiResponseDto = new ApiResponseValidationDto(ValidationUtils.getErrorMessages(result).toArray());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User userByID = userService.getUserByID(user.getId());
        if (userByID == null) {
            Object[] data = {};
            ApiResponseDto apiResponseDto = new ApiResponseDto(ResponseStatusCode.NOT_FOUND, "id not found", data);
            return new ResponseEntity<>(apiResponseDto, HttpStatus.NOT_FOUND);
        }

        userService.deleteUser(user.getId());
        Object[] data = {};
        ApiResponseDto apiResponseDto = new ApiResponseDto(ResponseStatusCode.OK, "success", data);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);

    }
}
