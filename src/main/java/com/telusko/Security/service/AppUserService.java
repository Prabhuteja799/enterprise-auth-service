package com.telusko.Security.service;


import com.telusko.Security.exception.ExceptionCode;
import com.telusko.Security.exception.InvalidRoleException;
import com.telusko.Security.exception.UserException;
import com.telusko.Security.model.AppUser;
import com.telusko.Security.model.Roles;
import com.telusko.Security.repository.AppUserRepo;
import com.telusko.Security.repository.RolesRepo;
import com.telusko.Security.request.UserRequest;
import com.telusko.Security.responseDTO.AppUserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AppUserService {

    private final AppUserRepo appUserRepo;
    private final PasswordEncoder passwordEncoder;
    private final RolesRepo rolesRepo;

    public AppUserService(AppUserRepo appUserRepo , PasswordEncoder passwordEncoder, RolesRepo rolesRepo){
        this.appUserRepo = appUserRepo;
        this.passwordEncoder= passwordEncoder;
        this.rolesRepo=rolesRepo;
    }


    public AppUserDTO getAppUserByName(String userName) {

        AppUser user =  appUserRepo.findByUserName(userName).orElseThrow(()->
                new UserException(ExceptionCode.USER_NOT_FOUND));
        return user.toAppUserDTO();
    }

    public  AppUserDTO createUser(UserRequest request) {

        Set<Roles> roles = new HashSet<>
                (rolesRepo.findAllByRoles(request.getRoles()));

        if(roles.size()!=request.getRoles().size()){
            throw new InvalidRoleException(ExceptionCode.INVALID_ROLE);
        }


        AppUser user = AppUser.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();

        return appUserRepo.save(user).toAppUserDTO();


    }
}
