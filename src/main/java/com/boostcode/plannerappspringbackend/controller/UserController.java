package com.boostcode.plannerappspringbackend.controller;

import com.boostcode.plannerappspringbackend.dto.request.UserRequestDTO;
import com.boostcode.plannerappspringbackend.dto.response.UserResponseDTO;
import com.boostcode.plannerappspringbackend.entity.User;
import com.boostcode.plannerappspringbackend.repository.UserRepository;
import com.boostcode.plannerappspringbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Expose "/users" and return a list of users
    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    // Get a specific user id
    @GetMapping("/{user_id}")
    public User getUserById(@PathVariable("user_id") long user_id) {
        return userService.getUserById(user_id);
    }

    // add mapping for creating users
    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserRequestDTO user) {
        return userService.createUser(user);
    }

    @PutMapping("/{user_id}")
    public UserResponseDTO updateUser(@PathVariable("user_id") long user_id, @RequestBody UserRequestDTO user) {
        return userService.updateUser(user_id, user);
    }

    @DeleteMapping("/{user_id}")
    public void deleteUser(@PathVariable("user_id") long user_id){
        userService.deleteUserById(user_id);
    }

}
