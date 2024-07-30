package com.example.newProject.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import  com.example.newProject.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="manager")

public class Manager {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name="managerid",updatable = false,nullable = false)

    private UUID managerId;

    @Column(name="managername")
    private String managerName;

    @OneToMany
    @JoinColumn(name = "managerid")
    @JsonIgnoreProperties("managerId")

    private List<User> users=new ArrayList<>();

    public Manager(){}

    public Manager(UUID managerId,String managerName,List<User> users){
        this.managerId=managerId;
        this.managerName=managerName;
        this.users=users;
    }

    public UUID getManagerId(){
        return  managerId;
    }

    public void setManagerId(UUID managerId){
        this.managerId=managerId;
    }

    public String getManagerName(){
        return managerName;
    }

    public void setManagerName(String managerName){
        this.managerName=managerName;
    }

    public List<User> getUsers(){
        return users;
    }

    public void setUsers(List<User> users){
        this.users=users;
    }


}
