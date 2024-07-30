package com.example.newProject.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.example.newProject.model.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User,UUID>{

}

