package com.example.newProject.service;
import com.example.newProject.model.Manager;
import com.example.newProject.repository.UserJpaRepository;
import com.example.newProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.newProject.model.User;
import com.example.newProject.repository.ManagerJpaRepository;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Array;
import java.util.*;

import com.example.newProject.repository.ManagerJpaRepository;

@Service
public class UserJpaService implements UserRepository {
    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    ManagerJpaRepository managerJpaRepository;



    @Override
    public ArrayList < User > getAllUsers() {
        List < User > userList = userJpaRepository.findAll();
        ArrayList < User > users = new ArrayList < > (userList);
        return users;
    }


    public User validatingUserByMobileNumber(String mobileNum){
        List<User> usersList = userJpaRepository.findAll();
        for(User user : usersList){
            if(mobileNum.equals(user.getMobNum())){
                return user;
            }
        }
        return null;
    }

    @Override
    public ArrayList<User> getUser(User userDetails){
        ArrayList<User> users = new ArrayList<>();
        User existingUser = null;
        if(userDetails.getUserId() != null) {
            try {
                existingUser = userJpaRepository.findById(userDetails.getUserId()).get();
                users.add(existingUser);
            } catch (NoSuchElementException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid User");
            }
        }
        else if(userDetails.getMobNum() != null) {
            existingUser = validatingUserByMobileNumber(userDetails.getMobNum());
            if (existingUser == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid User");
            }
            users.add(existingUser);
        }
        else if(userDetails.getManagerId() != null){
            try {
                Manager manager = managerJpaRepository.findById(userDetails.getManagerId()).get();
                List<User> managerUsers = manager.getUsers();
                users = new ArrayList<>(managerUsers);
            }
            catch (NoSuchElementException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Manager Details");
            }
        }
        return users;
    }

    public boolean validatingMobileNumber(String mobNumber){
        int lengthOfMobNum = mobNumber.length();
        if((lengthOfMobNum == 11 && mobNumber.startsWith("0")) ||  (lengthOfMobNum == 13 && mobNumber.startsWith("+91")) || (lengthOfMobNum == 10)){
            int i=lengthOfMobNum-1;
            for(int j=0;j<10;j++){
                if(!Character.isDigit(mobNumber.charAt(i))){
                    return false;
                }
                i=i-1;
            }
            return true;
        }
        else{
            return false;
        }

    }

    public boolean validatingPanNumber(String panNumber){
        for(char ch : panNumber.toCharArray()){
            if(!Character.isLetter(ch)  &&   !Character.isDigit(ch)) {
                return false;
            }
        }
        return panNumber.length() == 10;
    }

    public String getModifiedMobileNumber(String mobNumber){
        int i=mobNumber.length()-1;
        String modifiedMobileNumber = "";
        for(int j=0;j<10;j++){
            modifiedMobileNumber = mobNumber.charAt(i)+modifiedMobileNumber;
            i=i-1;
        }
        return modifiedMobileNumber;
    }

    public boolean validateManagerId(UUID managerId){
        if(managerId == null){
            return true;
        }
        else{
            List<Manager> managersList =  managerJpaRepository.findAll();
            for(Manager manager : managersList){
                if(managerId.equals(manager.getManagerId())){
                    return true;
                }
            }
            return false;
        }
    }

    public boolean isExistingMobileNum(String modifiedMobileNumber){
        List<User> users = userJpaRepository.findAll();
        for(User user : users){
            if(modifiedMobileNumber.equals(user.getMobNum())){
                return true;
            }
        }
        return false;
    }

    public boolean isExistingPanNumber(String panNumber){
        List<User> users = userJpaRepository.findAll();
        for(User user : users){
            if(panNumber.equals(user.getPanNum())){
                return true;
            }
        }
        return false;
    }

    @Override
    public User addUser(User userDetails){
        String userFullName = userDetails.getFullName();
        if(!userFullName.isEmpty()){
            userDetails.setFullName(userFullName);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not a Valid Name");
        }

        String mobNumber = userDetails.getMobNum();
        if(validatingMobileNumber(mobNumber)){
            String modifiedMobileNumber = getModifiedMobileNumber(mobNumber);
            if(isExistingMobileNum(modifiedMobileNumber)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User with this Mobile Number already exist");
            }
            userDetails.setMobNum(modifiedMobileNumber);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not a Valid Mobile Number");
        };

        String panNumber = userDetails.getPanNum();
        if(validatingPanNumber(panNumber)){
            if(isExistingPanNumber(panNumber.toUpperCase())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User with this PAN number already exist");
            }
            userDetails.setPanNum(panNumber.toUpperCase());
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not a Valid PAN Number");
        }

        UUID managerId = userDetails.getManagerId();
        if(validateManagerId(managerId)){
            userDetails.setManagerId(managerId);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not a Valid Manager Id");
        }
        userJpaRepository.save(userDetails);
        return userDetails;
    }

    @Override
    public void deleteUser(User userDetails){
        if(userDetails.getUserId() != null) {
            try {
                userJpaRepository.findById(userDetails.getUserId()).get();
                userJpaRepository.deleteById(userDetails.getUserId());

            } catch (NoSuchElementException e) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Invalid User");
            }
        }
        else if(userDetails.getMobNum() != null) {
            User existingUser = validatingUserByMobileNumber(userDetails.getMobNum());
            if (existingUser == null) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Invalid User");
            }
            userJpaRepository.deleteById(existingUser.getUserId());
        }
    }

    @Override
    public ArrayList<User> updateUsersInBulk(ArrayList<User> usersList){
//        ArrayList<UUID> managerIds = new ArrayList<>();
        ArrayList<UUID> userIds = new ArrayList<>();

        for(User eachUser : usersList){

            if(eachUser.getManagerId() == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"These  can be updated on a individual basis only and not in bulk");
            }
            try{
                managerJpaRepository.findById(eachUser.getManagerId()).get();
                userIds.add(eachUser.getUserId());
            }
            catch(NoSuchElementException e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"One or more managers are NOT FOUND");
            }
        }

        List<User> existingUsersList = userJpaRepository.findAllById(userIds);
        if(existingUsersList.size() != userIds.size()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"One or more Users are NOT FOUND");
        }


        User existingUser = null;
        ArrayList<User> updatedUsersList = new ArrayList<>();
        for(User user : usersList){
            existingUser = userJpaRepository.findById(user.getUserId()).get();
            if(existingUser.getManagerId() != null){
                existingUser.setIsActive(false);
            }
            if(user.getFullName() != null){
                String userFullName = user.getFullName();
                if(!userFullName.isEmpty()){
                    existingUser.setFullName(userFullName);
                }
                else{
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not a Valid Name");
                }
            }
            if(user.getMobNum() != null){
                String mobNumber  = user.getMobNum();
                if(validatingMobileNumber(mobNumber)){
                    String modifiedMobileNumber = getModifiedMobileNumber(mobNumber);
                    if(isExistingMobileNum(modifiedMobileNumber)){
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"One or more users had  Mobile Number that already exist");
                    }
                    existingUser.setMobNum(modifiedMobileNumber);
                }
                else{
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not a Valid Mobile Number");
                };
            }

            if(user.getPanNum() != null){
                String panNumber = user.getPanNum();
                if(validatingPanNumber(panNumber)){
                    if(isExistingPanNumber(panNumber.toUpperCase())){
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"One or more users had  PAN Number that already exist");
                    }
                    existingUser.setPanNum(panNumber.toUpperCase());
                }
                else{
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not a Valid PAN Number");
                }
            }
            existingUser.setManagerId(user.getManagerId());
            updatedUsersList.add(existingUser);
        }
        userJpaRepository.saveAll(updatedUsersList);
        return updatedUsersList;
    }
}