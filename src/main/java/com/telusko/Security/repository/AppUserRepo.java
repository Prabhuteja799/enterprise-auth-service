package com.telusko.Security.repository;

import com.telusko.Security.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser,String> {


    Optional <AppUser >findByUserName(String userName);
}
