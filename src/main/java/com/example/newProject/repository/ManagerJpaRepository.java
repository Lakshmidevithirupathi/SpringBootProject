package com.example.newProject.repository;
import  com.example.newProject.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface ManagerJpaRepository extends JpaRepository<Manager, UUID> {

}
