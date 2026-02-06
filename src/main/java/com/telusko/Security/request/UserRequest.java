package com.telusko.Security.request;

import com.telusko.Security.model.AppUser;
import com.telusko.Security.model.RoleName;
import com.telusko.Security.model.Roles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Set;


@Data
@Builder
public class UserRequest {


    @NotBlank(message = "Username shouldn't be blank")
    String username;

    @NotBlank(message = "Password Shouldn't be blank")
    String password;

    @NotNull
    Set<RoleName> roles;


    public AppUser toAppUser(Set<Roles> roles1) {


        return AppUser.builder()
                .username(this.username)
                .password(this.password)
                .roles(roles1)
                .build();

    }

}
