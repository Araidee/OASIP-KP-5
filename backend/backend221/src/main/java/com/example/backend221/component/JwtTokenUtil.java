package com.example.backend221.component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_30MIN = 30 * 60 * 1000;
    public static final long JWT_TOKEN_DAY = 24 * 60 * 60 * 1000;
    private int refreshExpirationDateInMs;

    @Value("${jwt.secret}")
    private String secret;
    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {

        return getClaimFromToken(token, Claims::getSubject);
    }
    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {

        return getClaimFromToken(token, Claims::getExpiration);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    //generate token for user
    public String generateToken(UserDetails userDetails,String name) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername(),userDetails.getAuthorities(),name,0);
    }
    public String generateRefreshToken(UserDetails userDetails, String name) {
        HashMap<String, Object> payload = new HashMap<>();
        return doGenerateToken(payload, userDetails.getUsername(), userDetails.getAuthorities(), name, 1);
    }
    //while creating the token -
//1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
//2. Sign the JWT using the HS512 algorithm and secret key.
//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
//   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject, Collection<? extends GrantedAuthority> roles, String name,long time) {
        if(time==1){
            time = JWT_TOKEN_DAY;
        }else {
            time = JWT_TOKEN_30MIN;
        }
        claims.put("name", name);
        claims.put("role",((GrantedAuthority)roles.stream().findFirst().get()).toString());
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + time)) // 30 นาที
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
    public String doGenerateRefreshToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_30MIN))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    @Value("${jwt.refreshExpirationDateInMs}")
    public void setRefreshExpirationDateInMs(int refreshExpirationDateInMs) {
        this.refreshExpirationDateInMs = refreshExpirationDateInMs;
    }
    public String doGenerateTokenForMs(Map<String, Object> claims, String email, String role, String name, long time) {
        if(time == 1){
            time = JWT_TOKEN_DAY;
        } else {
            time = JWT_TOKEN_30MIN;
        }

        claims.put("name", name);
        claims.put("role", role);
        System.out.println(claims);
        return Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)) // 5 ชั่วโมง
                .setExpiration(new Date(System.currentTimeMillis() + time)) // 30 นาที or 1 day
                .signWith(SignatureAlgorithm.HS512, secret).compact();

    }
}
