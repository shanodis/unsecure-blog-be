package me.project.repository;

import me.project.entitiy.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MyUserRepository extends JpaRepository<MyUser, UUID> {
    MyUser findUserByUsername(String username);
}
