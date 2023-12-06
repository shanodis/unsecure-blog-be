package me.project.service;

import lombok.AllArgsConstructor;
import me.project.auth.enums.AppUserRole;
import me.project.entitiy.MyUser;
import me.project.repository.MyUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MyUserService {
    private final MyUserRepository myUserRepository;

    public MyUser createMyUser(String username, String password, AppUserRole role) {
        MyUser myUser = new MyUser();
        myUser.setUsername(username);
        myUser.setPassword(password);
        myUser.setAppUserRole(role);
        return myUserRepository.save(myUser);
    }

    public List<MyUser> getAllUsers() {
        return myUserRepository.findAll();
    }
}
