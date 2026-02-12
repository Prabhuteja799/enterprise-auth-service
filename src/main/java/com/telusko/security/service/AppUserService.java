package com.telusko.security.service;


import com.telusko.security.exception.ExceptionCode;
import com.telusko.security.exception.InvalidRoleException;
import com.telusko.security.exception.UserException;
import com.telusko.security.model.AppUser;
import com.telusko.security.model.RoleName;
import com.telusko.security.model.Role;
import com.telusko.security.repository.AppUserRepo;
import com.telusko.security.repository.RolesRepo;
import com.telusko.security.request.PatchUserRolesRequest;
import com.telusko.security.request.UpdateUserRolesRequest;
import com.telusko.security.request.UserRequest;
import com.telusko.security.responseDTO.AppUserDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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


    @Transactional(readOnly = true)
    public AppUserDTO getAppUserByName(String username) {

        AppUser user =  appUserRepo.findNameofUserWithRoles(username).orElseThrow(()->
                new UserException(ExceptionCode.USER_NOT_FOUND));
        return toAppUserDTO(user);
    }

    @Transactional()
    public  AppUserDTO createUser(UserRequest request) {


        Optional<AppUser> existed = appUserRepo.findByUsername(request.getUsername());
        if(existed.isPresent()){
            log.warn("User already exists with username={}", request.getUsername());
            throw new UserException(ExceptionCode.USER_ALREADY_EXISTS);
        }

        Role defaulrole = rolesRepo.findByRole(RoleName.ROLE_USER).
                orElseThrow(()->new InvalidRoleException(ExceptionCode.INVALID_ROLE));

        AppUser user = AppUser.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        user.addRole(defaulrole);

        AppUser saved = appUserRepo.save(user);
        return toAppUserDTO(saved);



    }




    @Transactional
    public  AppUserDTO updateUserRolesByUserId( String userId, UpdateUserRolesRequest rolesRequest) {

        //find if user exists
        AppUser existed = appUserRepo.findByIdWithRoles(userId)
                .orElseThrow(()->new UserException(ExceptionCode.USER_NOT_FOUND));

        existed.clearRoles();

        for(RoleName roleName : rolesRequest.roles()){

            Role role = rolesRepo.findByRole(roleName).
                    orElseThrow(()->new InvalidRoleException(ExceptionCode.INVALID_ROLE));

            existed.addRole(role);

        }

        return toAppUserDTO(existed);

    /**
                //get roles objcet from Roles
                Set<Roles> roles =new HashSet<>( rolesRepo.findAllByRoleIn(rolesRequest.roles()));


                if(roles.size() != rolesRequest.roles().size()){
                    throw new InvalidRoleException(ExceptionCode.INVALID_ROLE);
                }

                //clear all the previous Roles
                existed.clearRoles();
                //Now we need to add role to UserRole ,so then we can add userRole to user
                roles.forEach(existed::addRole);
        **/

       // return appUserRepo.save(existed).toAppUserDTO();

    }

    @Transactional
    public  AppUserDTO patchRolesByUserId(@Valid String userId, PatchUserRolesRequest rolesRequest) {

        //find if user exist
        AppUser user = appUserRepo.findByIdWithRoles(userId)
                .orElseThrow(()->new UserException(ExceptionCode.USER_NOT_FOUND));
        //iterate through add roles,check whether they exists and add roles to user

        Set<RoleName> add = rolesRequest.add();
        if(add != null) {
            for(RoleName roleName : add){
                Role role = rolesRepo.findByRole(roleName).
                        orElseThrow(()->new InvalidRoleException(ExceptionCode.INVALID_ROLE));
                user.addRole(role);

            }
        }

        //Iterate thorugh remove roles  and remove roles from user

        Set<RoleName> remove = rolesRequest.remove();
        if(remove != null) {
            for (RoleName roleName : remove) {
                Role role = rolesRepo.findByRole(roleName)
                        .orElseThrow(() -> new InvalidRoleException(ExceptionCode.INVALID_ROLE));

                user.removeRole(role);
            }
        }

        return toAppUserDTO(user);
    }



    private AppUserDTO toAppUserDTO( AppUser user){

        Set<RoleName> roles = user.getUserRoles().stream()
                .map(ur-> ur.getRole().getRole() )
                .collect(Collectors.toSet());

        return  new AppUserDTO(user.getId(), user.getUsername(), roles);
    }

}

