package com.telusko.security.controller;


import com.telusko.security.request.UserRequest;
import com.telusko.security.responseDTO.AppUserDTO;
import com.telusko.security.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
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


    @PostMapping("/users")
    public ResponseEntity<AppUserDTO> addUser(@Valid @RequestBody UserRequest userRequest){
        return ResponseEntity.ok(appUserService.createUser(userRequest));
    }
}
