package com.telusko.security.config;

import com.telusko.security.request.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private SecretKey key;
    private String issuer;
    private long accessExpiration;
    private long refreshExpiration;


    public JwtUtil(JwtProperties jwtProperties){
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secret()));
        this.issuer = jwtProperties.issuer();
        this.accessExpiration= jwtProperties.accessExpiration();
        this.refreshExpiration= jwtProperties.refreshExpiration();

    }


    public String generateAcessToken(UserDetails userDetails){

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        Date now= new Date();
        Date expiry = new Date(now.getTime()+accessExpiration);

        return Jwts.builder().
                subject(userDetails.getUsername())
                .claim("roles", roles)
                .claim("type", "ACCESS")
                .issuer(issuer)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key)
                .compact();

    }

    public Claims parseClaims(String token){
        return Jwts.parser()
                .verifyWith(key)
                .requireIssuer(issuer)   //this is optional,only if you want to check issuer when using diff servers (prod/dev)
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }


    public String extractUsername(String token) {

        Claims claims = parseClaims(token);
        return claims.getSubject();
    }

    public boolean isAcesstokenValid(String token, UserDetails userDetails) {

        Claims claims = parseClaims(token);
        return (userDetails.getUsername().equals(claims.getSubject() )
        && "ACCESS".equals(claims.get("type")))
            && !expired(claims.getExpiration());

    }

    private boolean expired(Date expiration) {
        return expiration.before(new Date());
    }
}
