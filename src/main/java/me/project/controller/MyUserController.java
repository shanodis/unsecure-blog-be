package me.project.controller;

import lombok.AllArgsConstructor;
import me.project.auth.enums.AppUserRole;
import me.project.dto.MyUserRequest;
import me.project.entitiy.MyUser;
import me.project.service.MyUserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class MyUserController {
    private final MyUserService myUserService;

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/all")
    public List<MyUser> getAllUsers() {
        return myUserService.getAllUsers();
    }

    // HACKABLE
//    @GetMapping
//    public MyUser createMyUser(
//            @RequestParam String username,
//            @RequestParam String password,
//            @RequestParam AppUserRole role
//    ) {
//        return myUserService.createMyUser(username, password, role);
//    }

    // SECURED
    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public MyUser createMyUser(@RequestBody MyUserRequest request) {
        return myUserService.createMyUser(request.getUsername(), request.getPassword(), request.getRole());
    }
}
