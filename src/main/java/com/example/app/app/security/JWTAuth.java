package com.example.app.app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTAuth {
    @Autowired
    private CustomUserDetailsService userDetailsService;
    private final long EXPIRATION_TIME=1000*60*60;
    private final String key = "this-is-a-very-long-and-easy-to-remember-secret-key-but-please-do-not-use-it-in-production-it-is-not-secure-enough";
    private final SecretKey secretKey= Keys.hmacShaKeyFor(key.getBytes());

    public String generateToken(String username){
        HashMap<String,Object> claims=new HashMap<>();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        claims.put("roles",authorities);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();





    }

    public String getUserName(String token){
        Claims body = extractClaims(token);
        return body.getSubject();


    }

    public Date extractExpiration(String token){
        Claims body = extractClaims(token);
        return body.getExpiration();


    }


    private  boolean isTokenExpired(String token){
    return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String username,UserDetails userDetails,String token){
        return username.equals(userDetails.getUsername()) && ! isTokenExpired(token);


    }


    private Claims extractClaims(String token){

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

    }



}
