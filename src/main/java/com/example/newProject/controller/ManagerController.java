package com.example.newProject.controller;

import com.example.newProject.model.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.web.bind.annotation.*;
import  com.example.newProject.service.ManagerJpaService;
import java.util.*;


@RestController
public class ManagerController {

    @Autowired
    private ManagerJpaService managerService;

    @GetMapping("/managers")
    public ArrayList<Manager> getAllManagers(){
        return managerService.getAllManagers();
    }

    @GetMapping("/managers/{managerId}")
    public  Manager getManagerById(@PathVariable("managerId") UUID managerId){
        return  managerService.getManagerById(managerId);
    }

    @PostMapping("/managers")
    public Manager addManager(@RequestBody Manager manager){
        return  managerService.addManager(manager);
    }

    @PutMapping("/managers/{managerId}")
    public Manager updateManager(@PathVariable("managerId") UUID managerId,@RequestBody Manager manager){
        return managerService.updateManager(managerId,manager);
    }

    @DeleteMapping("/managers/{managerId}")
    public void deleteManager(@PathVariable UUID managerId){
        managerService.deleteManager(managerId);
    }

}
