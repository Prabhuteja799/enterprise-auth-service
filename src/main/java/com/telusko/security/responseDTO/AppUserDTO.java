package com.telusko.security.responseDTO;


import com.telusko.security.model.RoleName;
import lombok.Builder;

import java.util.Set;


public record AppUserDTO (
        String id,
        String username,
        Set<RoleName> roles){}




//    public AppUserDTO(String id, String username, String password) {
//        this.id = id;
//        username = username;
//        Password = password;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        username = username;
//    }
//
//    public String getPassword() {
//        return Password;
//    }
//
//    public void setPassword(String password) {
//        Password = password;
//    }
//}
