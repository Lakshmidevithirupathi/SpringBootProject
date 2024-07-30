package com.example.newProject.controller;


import com.example.newProject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.newProject.service.UserJpaService;
import java.util.ArrayList;

@RestController
public class UserController {
    @Autowired
    UserJpaService userJpaService;

    @GetMapping("/all_users")
    public ArrayList<User> getAllUsers(){
        return userJpaService.getAllUsers();
    }

    @PostMapping("/get_user")
    public ArrayList<User> getUser(@RequestBody User user){
        return userJpaService.getUser(user);
    }

    @PostMapping("/create_user")
    public User addUser(@RequestBody User user){
        return userJpaService.addUser(user);
    }

    @PostMapping("/delete_user")
    public void deleteUser(@RequestBody User user){
        userJpaService.deleteUser(user);
    }

    @PostMapping("/update_user")
    public ArrayList<User> updateUsersInBulk(@RequestBody ArrayList<User> usersList){
        return userJpaService.updateUsersInBulk(usersList);
    }
}