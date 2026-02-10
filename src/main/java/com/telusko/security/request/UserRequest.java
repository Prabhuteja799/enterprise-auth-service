package com.telusko.security.request;

import com.telusko.security.model.AppUser;
import com.telusko.security.model.RoleName;
import com.telusko.security.model.Roles;
import com.telusko.security.model.UserRoles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import javax.management.relation.Role;
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




}
