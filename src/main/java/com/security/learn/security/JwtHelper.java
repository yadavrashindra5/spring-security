package com.security.learn.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
public class JwtHelper {

    public static final long TOKEN_VALIDITY=5*60*60*1000;

    public static final String SECRET_KEY="hgjkljhsdjhfhjfgdshgfsgdjfgsdjgfhjsgdfgsdjfgjhdsgfjgihiuhiiuiuuiiouidsjfghjdsgfjgsdfsdfhjdsbfj";

    //retrieve username from jwt token

    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver) {
        final Claims claims=getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        //Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody()

        //latest
//        SignatureAlgorithm hs512= SignatureAlgorithm.HS512;
//        SecretKeySpec secretKeySpec=new SecretKeySpec(SECRET_KEY.getBytes(),hs512.getJcaName());
//        return (Claims) Jwts.parser().verifyWith(secretKeySpec).build().parse(token).getPayload();
        return Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getPayload();
    }

    public Boolean isTokenExpired(String token){
        final Date expiration=getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token,Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Objects>claims=new HashMap<>();
        return doGenerateToken(claims,userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Objects> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY).compact();
    }

}
