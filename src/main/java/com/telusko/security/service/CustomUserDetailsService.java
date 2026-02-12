package com.telusko.security.service;

import com.telusko.security.model.AppUser;
import com.telusko.security.model.CustomUserDetails;
import com.telusko.security.repository.AppUserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private AppUserRepo appUserRepo;


    public CustomUserDetailsService (AppUserRepo appUserRepo){
        this.appUserRepo = appUserRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepo.findNameofUserWithRoles(username)
                .orElseThrow(()->new UsernameNotFoundException("Username is Not Found"));

        return  new CustomUserDetails(user);
    }
}
