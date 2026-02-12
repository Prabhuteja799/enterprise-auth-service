package com.telusko.security.request;

import com.telusko.security.model.RoleName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Constructor;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {


    @NotBlank(message = "Username shouldn't be blank")
    String username;

    @NotBlank(message = "Password Shouldn't be blank")
    String password;




}
