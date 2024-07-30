package com.example.newProject.service;
import com.example.newProject.model.Manager;
import  com.example.newProject.repository.ManagerJpaRepository;
import  com.example.newProject.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import  java.util.*;

@Service

public class ManagerJpaService implements ManagerRepository {

    @Autowired
    private  ManagerJpaRepository managerJpaRepository;

    @Override
    public ArrayList<Manager> getAllManagers(){
        List<Manager> managerList= managerJpaRepository.findAll();
        ArrayList<Manager> managers=new ArrayList<>(managerList);
        return  managers;
    }

    @Override

    public  Manager getManagerById(UUID managerId){

        try{
            Manager manager=managerJpaRepository.findById(managerId).get();
            return manager;
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Manager addManager(Manager manager){
        managerJpaRepository.save(manager);
        return  manager;
    }

    @Override

    public Manager updateManager(UUID managerId,Manager manager){

        try{
            Manager newManager= managerJpaRepository.findById(managerId).get();

            if(manager.getManagerName() != null){
                newManager.setManagerName(manager.getManagerName());
            }

            managerJpaRepository.save(newManager);
            return newManager;
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteManager(UUID managerId){
        try{
            managerJpaRepository.deleteById(managerId);

        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw  new ResponseStatusException(HttpStatus.NO_CONTENT);
    }
}
