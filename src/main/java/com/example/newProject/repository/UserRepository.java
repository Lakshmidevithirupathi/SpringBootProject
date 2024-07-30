package com.example.newProject.repository;

import java.util.*;
import com.example.newProject.model.User;


public interface UserRepository {

    ArrayList < User > getAllUsers();


    ArrayList<User> getUser(User userDetails);

    User addUser(User userDetails);

    void deleteUser(User userDetails);

    ArrayList<User> updateUsersInBulk(ArrayList<User> usersList);

}
