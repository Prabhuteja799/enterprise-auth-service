package com.telusko.security.controller;

import com.telusko.security.config.JwtUtil;
import com.telusko.security.config.TokenHashUtil;
import com.telusko.security.exception.AuthenticationException;
import com.telusko.security.exception.ExceptionCode;
import com.telusko.security.model.RefreshToken;
import com.telusko.security.repository.RefreshTokenRepo;
import com.telusko.security.request.LoginRequest;
import com.telusko.security.request.RefreshRequest;
import com.telusko.security.response.AuthResponse;
import com.telusko.security.response.RefreshResponse;
import com.telusko.security.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private CustomUserDetailsService userDetailsService;
    private RefreshTokenRepo tokenRepo;
    private TokenHashUtil tokenHashUtil;


    public LoginController(AuthenticationManager authenticationManager, JwtUtil jwtUtil , CustomUserDetailsService customUserDetailsService,
                           RefreshTokenRepo tokenRepo , TokenHashUtil tokenHashUtil){

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = customUserDetailsService;
        this.tokenRepo=tokenRepo;
        this.tokenHashUtil = tokenHashUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest , HttpServletRequest request){

        UsernamePasswordAuthenticationToken  token = new
                UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());

        Authentication auth = authenticationManager.authenticate(token);

        String jwtToken = jwtUtil.generateAcessToken((UserDetails) auth.getPrincipal());
        String refreshToken = jwtUtil.generateRefreshToken((UserDetails) auth.getPrincipal());

        String hashedToken = tokenHashUtil.hash(refreshToken);
        Claims claims = jwtUtil.parseClaims(refreshToken);

        RefreshToken entity = RefreshToken.builder().jti(claims.getId())
                .username(claims.getSubject())
                .tokenHash(hashedToken)
                .revoked(false)
                .expiryDate(claims.getExpiration().toInstant())
                .deviceInfo(request.getHeader("User-Agent"))
                .ipAddress(request.getRemoteAddr())
                .build();

        tokenRepo.save(entity);
        return ResponseEntity.ok(new AuthResponse(jwtToken, refreshToken));


    }


    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse>  refreshToken(@RequestBody RefreshRequest refreshRequest , HttpServletRequest request)  {

        String refreshToken = refreshRequest.refreshToken();


        if(!jwtUtil.isRefreshTokenValid(refreshToken )){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        RefreshToken storedToken= tokenRepo.findByTokenHash(tokenHashUtil.hash(refreshToken))
                .orElseThrow(()-> new AuthenticationException(ExceptionCode.INVALID_REFRESHTOKEN));

        if(storedToken.getRevoked()){
            tokenRepo.revokeAllUsers(storedToken.getUsername()); //revoking all tokens on reuse
            throw new AuthenticationException(ExceptionCode.OLD_REFRESHTOKEN); //ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        storedToken.setRevoked(true);
        tokenRepo.save(storedToken);

        String username = jwtUtil.extractUsername(refreshToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);


        String newAccess = jwtUtil.generateAcessToken(userDetails);
        String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);

        String hashedToken = tokenHashUtil.hash(newRefreshToken);

        Claims claims = jwtUtil.parseClaims(newRefreshToken);

        RefreshToken entity = RefreshToken.builder().jti(claims.getId())
                .username(username)
                .tokenHash(hashedToken)
                .revoked(false)
                .expiryDate(claims.getExpiration().toInstant())
                .deviceInfo(request.getHeader("User-Agent"))
                .ipAddress(request.getRemoteAddr())
                .build();

        tokenRepo.save(entity);
        return ResponseEntity.ok(new RefreshResponse(newAccess , newRefreshToken));


    }
}
