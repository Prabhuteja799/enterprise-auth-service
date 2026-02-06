package com.telusko.Security.responseDTO;


import com.telusko.Security.model.RoleName;
import lombok.Builder;

import java.util.Set;

@Builder
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
