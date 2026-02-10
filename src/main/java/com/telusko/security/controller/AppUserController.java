package com.telusko.security.controller;


import com.telusko.security.request.PatchUserRolesRequest;
import com.telusko.security.request.UpdateUserRolesRequest;
import com.telusko.security.request.UserRequest;
import com.telusko.security.responseDTO.AppUserDTO;
import com.telusko.security.service.AppUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.sound.midi.Patch;

@RestController
@Validated
//@RequestMapping("/user")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService){
        this.appUserService = appUserService;
    }


    @GetMapping("/users/{username}" )
    public ResponseEntity<AppUserDTO> getAppUser(@PathVariable("username") String username ){
        return ResponseEntity.ok(appUserService.getAppUserByName(username));
    }


    @PostMapping("/user")
    public ResponseEntity<AppUserDTO> addUser(@Valid @RequestBody UserRequest userRequest){
        return ResponseEntity.ok(appUserService.createUser(userRequest));
    }

    @PutMapping("/user/{user_id}/roles")
    public ResponseEntity<AppUserDTO> updateRoles(@NotBlank @PathVariable("user_id") String  userId, @Valid @RequestBody UpdateUserRolesRequest rolesRequest){
        return ResponseEntity.ok(appUserService.updateUserRolesByUserId(userId,rolesRequest));

    }

    @PatchMapping("user/{user_id}/roles")
    public ResponseEntity<AppUserDTO> patchRoles(@NotBlank @PathVariable("user_id") String userId,@Valid @RequestBody PatchUserRolesRequest rolesRequest){
        return ResponseEntity.ok(appUserService.patchRolesByUserId(userId,rolesRequest));
    }
}
