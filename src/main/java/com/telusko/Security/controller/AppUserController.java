package com.telusko.Security.controller;


import com.telusko.Security.request.UserRequest;
import com.telusko.Security.responseDTO.AppUserDTO;
import com.telusko.Security.service.AppUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
