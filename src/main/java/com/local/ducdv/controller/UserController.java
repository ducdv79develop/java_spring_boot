package com.local.ducdv.controller;

import com.local.ducdv.dto.UserDto;
import com.local.ducdv.entity.User;
import com.local.ducdv.exception.AppException;
import com.local.ducdv.model.UserModel;
import com.local.ducdv.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String index(Model model) {
        List<UserModel> users = userService.getUserList();
        model.addAttribute("users", users);
        return "user/index";
    }

    @GetMapping("/create")
    public String create() {
        return "user/create";
    }

    @PostMapping("/store")
    public String store(@RequestBody @Valid UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return "user/create";
        }

        Boolean status = userService.storeUser(userDto, null);
        if (status) {
            return "redirect:/user";
        }
        throw new AppException(500, "Server Error");
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        UserModel user = userService.getUserByID(id);
        if (user == null) {
            throw new AppException(404, "Not Found");
        }
        model.addAttribute("user", user);

        return "user/edit";
    }

    @PostMapping("/update")
    public String update(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "user/create";
        }

        return "redirect:/user";
    }
}
