package com.example.newProject.repository;
import com.example.newProject.model.Manager;


import  java.util.ArrayList;

import  java.util.*;

public interface ManagerRepository {

    ArrayList<Manager> getAllManagers();
    Manager getManagerById(UUID managerId);

    Manager addManager(Manager manager);

    Manager updateManager(UUID managerId,Manager manager);
    void deleteManager(UUID managerId);

}


