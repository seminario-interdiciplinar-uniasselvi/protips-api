package com.api.protips.configurations.auth;

import com.api.protips.configurations.ApplicationProperties;
import com.api.protips.models.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Clock;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtService {

  private final ApplicationProperties applicationProperties;
  private final Clock clock;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(User user) {
    final HashMap<String, Object> claims = new HashMap<>();
    claims.put("id", user.getId());
    return generateToken(claims, user);
  }

  public String generateToken(
    Map<String, Object> extraClaims,
    User user
  ) {
    final long jwtExpiration = Long.parseLong(applicationProperties.getJwt().getExpiration());
    return buildToken(extraClaims, user, jwtExpiration);
  }

  public String generateRefreshToken(
    User user
  ) {
    final long refreshExpiration = Long.parseLong(
      applicationProperties.getJwt().getRefreshExpiration());
    final HashMap<String, Object> claims = new HashMap<>();
    claims.put("id", user.getId());
    return buildToken(claims, user, refreshExpiration);
  }

  private String buildToken(
    Map<String, Object> extraClaims,
    User user,
    long expiration
  ) {
    return Jwts
      .builder()
      .setClaims(extraClaims)
      .setSubject(user.getUsername())
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + expiration))
      .signWith(getSignInKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  public boolean isTokenValid(String token, User user) {
    final String username = extractUsername(token);
    return (username.equals(user.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
      .parserBuilder()
      .setSigningKey(getSignInKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
  }

  private Key getSignInKey() {
    String secretKey = applicationProperties.getJwt().getSecretKey();
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

}
