package com.local.ducdv.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.local.ducdv.entity.User;
import com.local.ducdv.exception.AppException;
import com.local.ducdv.model.UserModel;
import com.local.ducdv.service.UserService;

import jakarta.validation.Valid;

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
    public String create(Model model) {
        model.addAttribute("user", new User());
        return "user/create";
    }

    @PostMapping(value = "/create")
    public String store(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/create";
        }

        User userSave = userService.storeUser(user, null);
        if (userSave !=  null) {
            return "redirect:/user";
        }
        throw new AppException(500, "Server Error");
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        User user = userService.getUserByID(id);
        if (user == null) {
            throw new AppException(404, "Not Found");
        }
        model.addAttribute("user", user);

        return "user/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid User user, BindingResult result, @PathVariable Integer id, Model model) {
        if (result.hasErrors()) {
            return "user/edit";
        }

        User userSave = userService.storeUser(user, id);
        if (userSave != null) {
            return "redirect:/user";
        }
        throw new AppException(500, "Server Error");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        Boolean status = userService.deleteUser(id);
        if (status) {
            return "redirect:/user";
        }
        throw new AppException(500, "Server Error");
    }
}
