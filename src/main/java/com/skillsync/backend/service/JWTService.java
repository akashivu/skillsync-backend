package com.skillsync.backend.service;

import com.skillsync.backend.model.User;
import com.skillsync.backend.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private Long jwtExpirationTimeInMs;
    @Autowired
    private UserRepository userRepository;
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+jwtExpirationTimeInMs))
                .signWith(getSigningKey(),SignatureAlgorithm.HS256)
                .compact();
  }
  public boolean validateToken(String username,String token){
        final String extractedusername=extractUsername(token);
        return (extractedusername.equals(username) &&  !isTokenExpired(token));

  }
  public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);

  }
  public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
  }
  public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
      final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
  }
  private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
  }
  public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
  }



}
