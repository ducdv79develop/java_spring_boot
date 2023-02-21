package com.local.ducdv.api;

import com.local.ducdv.entity.User;
import com.local.ducdv.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserApiController {
    private final UserRepository userRepository;
    public UserApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(path="/users")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }
}
