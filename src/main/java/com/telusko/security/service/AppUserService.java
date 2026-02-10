package com.telusko.security.service;


import com.telusko.security.exception.ExceptionCode;
import com.telusko.security.exception.InvalidRoleException;
import com.telusko.security.exception.UserException;
import com.telusko.security.model.AppUser;
import com.telusko.security.model.Roles;
import com.telusko.security.repository.AppUserRepo;
import com.telusko.security.repository.RolesRepo;
import com.telusko.security.request.UserRequest;
import com.telusko.security.responseDTO.AppUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
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


    public AppUserDTO getAppUserByName(String username) {

        AppUser user =  appUserRepo.findByUsernameWithRoles(username).orElseThrow(()->
                new UserException(ExceptionCode.USER_NOT_FOUND));
        return user.toAppUserDTO();
    }

    public  AppUserDTO createUser(UserRequest request) {


        Optional<AppUser> existed = appUserRepo.findByUsername(request.getUsername());
        if(existed.isPresent()){
            log.info("User exists alreadys {}", existed.toString());
            throw new UserException(ExceptionCode.USER_ALREADY_EXISTS);
        }

        Set<Roles> roles = new HashSet<>(rolesRepo.findAllByRolesIn(request.getRoles()));

        if(roles.size() != request.getRoles().size()){
            throw new InvalidRoleException(ExceptionCode.INVALID_ROLE);
        }

        AppUser user = AppUser.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        roles.stream().forEach(user::addRole);

        AppUser saved = appUserRepo.save(user);
        return saved.toAppUserDTO();



    }
}
