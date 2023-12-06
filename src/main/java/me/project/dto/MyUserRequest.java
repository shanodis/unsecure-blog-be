package me.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.project.auth.enums.AppUserRole;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyUserRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private AppUserRole role;
}
