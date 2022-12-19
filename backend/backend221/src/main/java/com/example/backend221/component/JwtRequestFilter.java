package com.example.backend221.component;

import java.io.IOException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend221.services.JwtUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        System.out.println("header: "+ requestTokenHeader + "  " + "No Token or Token is Invalid");
        String email = null;
        String jwtToken = null;
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                email = jwtTokenUtil.getUsernameFromToken(jwtToken);
                String isRefreshToken = request.getHeader("isRefreshToken");
                String requestURL = request.getRequestURL().toString();
                // allow for Refresh Token creation if following conditions are true.
                Claims claims = getAllClaimsFromToken(jwtToken);
                if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refresh"))
                {
                    System.out.println("1");
                    if(claims.getExpiration().getTime() - claims.getIssuedAt().getTime() == 86400000){
                        System.out.println("2");
                        if(claims.getExpiration().getTime() > Instant.now().toEpochMilli()) {
                            System.out.println("3");
                            allowForRefreshToken(claims, request);
                        }
                    }
                } 
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                if (e.getClaims().getExpiration().getTime() - e.getClaims().getIssuedAt().getTime() > 1800000) {
                    System.out.println("JWT REFRESH Token has expired");
                    request.setAttribute("message", "cannot refresh token. need to login again");
                } else {
                    System.out.println("JWT Token has expired");
                    request.setAttribute("message", "please send refresh token to /refresh to refresh token");
                    }
                }
            }else {
            logger.warn("JWT Token does not begin with Bearer String");
            }
        //เอาtoken มาแกะค่าแล้ว check token แต่ไม่ได้เช็คms โดยตรง
        if (StringUtils.hasText(jwtToken)) {
            List<GrantedAuthority> role = new ArrayList<GrantedAuthority>();
            role.add(new SimpleGrantedAuthority(getAllClaimsFromToken(jwtToken).get("role").toString().split("_")[0]));

            UserDetails userDetails = new User(jwtTokenUtil.getUsernameFromToken(jwtToken), "", role);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } else {
            System.out.println("Please log in for get Token again.");
            request.setAttribute("message", "Please log in for get Token again.");
        }
// Once we get the token validate it.
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(email);
    // if token is valid configure Spring Security to manually set
    // authentication
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
    @Value("${jwt.secret}")
    private String secret;
    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;

        }
        return claims;
    }

    private void allowForRefreshToken(Claims claims, HttpServletRequest request) {
        System.out.println("allowToken");
        // create a UsernamePasswordAuthenticationToken with null values.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                null, null, null);
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        // Set the claims so that in controller we will be using it to create
        // new JWT
        request.setAttribute("claims", claims);
    }
    @SneakyThrows
    public JSONObject extractMSJwt(String token) {
        String[] chunks = token.split("\\.");

        JSONObject header = new JSONObject(decode(chunks[0]));
        JSONObject payload = new JSONObject(decode(chunks[1]));
        String signature = decode(chunks[2]);
        if (payload.getString("iss").equals("https://login.microsoftonline.com/6f4432dc-20d2-441d-b1db-ac3380ba633d/v2.0")) {
            System.out.println("BEFORE CONFIG");

            DecodedJWT jwt = JWT.decode(token);
            JwkProvider provider = new UrlJwkProvider(new URL("https://login.microsoftonline.com/common/discovery/keys"));
            Jwk jwk = provider.get(jwt.getKeyId());
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
            algorithm.verify(jwt);

            System.out.println("AFTER CONFIG");
        }
        System.out.println("PAYLOAD : " + payload);
        return payload;
    }

    private static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }
}
