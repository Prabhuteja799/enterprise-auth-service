package com.telusko.security.repository;

import com.telusko.security.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken,String>{

    Optional<RefreshToken> findByTokenHash(String refreshToken);


    @Modifying
    @Query("update RefreshToken rt set rt.revoked=true where rt.username=:username and rt.revoked=false ")
    int revokeAllUsers(@Param("username") String username);
}
