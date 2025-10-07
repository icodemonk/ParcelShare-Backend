package com.parcelshare.parcelshare.Config;

import com.parcelshare.parcelshare.Repository.UsersRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class JwtServices {
    @Autowired
    UsersRepo usersRepo;

    private final String SECRET_KEY ="acdd2128cb82685b893957b82db592dff9b5e5093aa92d5731da5b7e8777025b";
    private final long EXPIRATION_TIME = 864000000;


    //genertate token

    public  String generateToken(Authentication authentication){

        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        String builder = Jwts.builder()
                .header()
                .add("TYPE","JWT")
                .and()
                .subject(userPrincipal.getUsername())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();

        return builder;


    }

    //getclaims
    public String getRolebyToken(String token){
        String username = getUsernameFromToken(token);
        return usersRepo.findByUsername(username).getRole().toString();
    }

    public Claims getClaimsFromToken(String token){
        Claims claims = null;

        try {
            claims=Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }catch (Exception e){
            throw new JwtException("Invalid token");
        }

        return claims;

    }


    //validatetoken
    public boolean validateToken(String token){

        Claims claims = getClaimsFromToken(token);

        return claims != null;

    }
    //uservalidation
    public Boolean userValidation(String token , UserDetails userDetails){
        String username =getUsernameFromToken(token);

        return username != null && username.equals(userDetails.getUsername());

    }

    //getUserfrom token
    public String getUsernameFromToken(String token){
        Claims claims = getClaimsFromToken(token);

        return claims.getSubject();
    }
    //





}
