package com.telusko.security.repository;

import com.telusko.security.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser,String> {


    Optional <AppUser > findByUsername(String userName);


    @Query(""" 
            SELECT u 
            FROM AppUser u 
            LEFT JOIN FETCH u.userRoles ur
            LEFT JOIN FETCH ur.role r
            WHERE u.username =:username
            """)
    Optional<AppUser> findByUsernameWithRoles(@Param("username") String username);
}
