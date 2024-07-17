package com.sunbase.javaassignment.securityconfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY="c6cb1cd5b26b780530b6f204ffe2b9522e0756c33796978f85ed65cc69b91680";

    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken , Claims::getSubject);
    }


    public String generateToken(UserDetails userDetails){
        return Jwts.
                builder()
                .claim("authorities", getClaims(userDetails.getAuthorities()))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+(60*1000*60*24)))
                .signWith(getSignInkey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private String getClaims(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authoritiesSet = new HashSet<>();
        for(GrantedAuthority authority: authorities) {
            authoritiesSet.add(authority.getAuthority());
        }
        //MEMBER, ADMIN
        return String.join(",", authoritiesSet);
    }



    public Boolean isTokenValid(String token, UserDetails userDetails){

        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && isTokenExpired(token));

    }

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInkey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    public  <T> T   extractClaim(String token , Function<Claims , T> claimsResolver){
        final Claims claims= extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public Key getSignInkey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    public Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken , Claims::getExpiration);
    }

}
