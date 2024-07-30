package com.example.newProject.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import  jakarta.persistence.*;
import  java.util.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;
@Entity
@Table(name="users")

public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name="userid",updatable = false,nullable = false)

    private UUID userId;

    @Column(name="fullname")
    private String fullName;

    @Column(name="mobnum")
    private String mobNum;

    @Column(name="pannum")
    private String panNum;


    @CreationTimestamp
    @Column(name = "createdat")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updatedat")
    private Timestamp updatedAt;

    @Column(name = "isactive")
    private boolean isActive = true;


    @Column(name = "managerid")

    private UUID managerId;


    public User(){}

    public User(UUID userId,String fullName, String mobNum,String panNum,Timestamp createdAt,Timestamp updatedAt,boolean isActive,UUID managerId){
        this.userId=userId;
        this.fullName=fullName;
        this.mobNum=mobNum;
        this.panNum=panNum;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;
        this.isActive=isActive;
        this.managerId=managerId;
    }

    public UUID getUserId(){
        return userId;
    }

    public void setUserId(UUID userId){
        this.userId=userId;
    }

    public String getFullName(){
        return  fullName;
    }

    public void  setFullName(String  fullName){
        this.fullName=fullName;
    }

    public String getMobNum(){
        return mobNum;
    }

    public void setMobNum(String mobNum){
        this.mobNum=mobNum;
    }

    public String getPanNum(){
        return panNum;
    }

    public void setPanNum(String panNum){
        this.panNum=panNum;
    }

    public Timestamp getCreatedAt(){
        return  createdAt;
    }
    public void setCreatedAt(Timestamp createdAt){
        this.createdAt=createdAt;
    }

    public Timestamp getUpdatedAt(){
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt){
        this.updatedAt=updatedAt;
    }
    public boolean getIsActive(){
        return  isActive;
    }
    public  void  setIsActive(boolean isActive){
        this.isActive=isActive;
    }

    public UUID getManagerId(){
        return  managerId;
    }

    public void setManagerId(UUID managerId){
        this.managerId=managerId;
    }

}
