package br.com.magazineluiza.v1.customers.security;

import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

  /**
   * THIS IS NOT A SECURE PRACTICE! For simplicity, we are storing a static key here. Ideally, in a
   * microservices environment, this key would be kept on a config-server.
   */
  @Value("${security.jwt.token.secret-key:secret-key}")
  private String secretKey;

  @Value("${security.jwt.token.expire-length:#{null}}")
  private Optional<Long> validityInMilliseconds;
  
  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken() {

    Date now = new Date();
    
    JwtBuilder builder = Jwts.builder()
        .setIssuedAt(now)
        .signWith(SignatureAlgorithm.HS256, secretKey);
    
    if(validityInMilliseconds.isPresent()) {
    	Date validity = new Date(now.getTime() + validityInMilliseconds.get());
    	builder = builder.setExpiration(validity);
    }
    
    return builder.compact();
  }

  public Optional<String> resolveToken(ServletRequest servletRequest) {
	  Optional<String> token;
	  HttpServletRequest req = (HttpServletRequest)servletRequest;
	  String bearerToken = req.getHeader("Authorization");
	  if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
		  token = Optional.of(bearerToken.substring(7));
	  } else {
		  token = Optional.empty();
	  }
	  return token;
  }

  public boolean validateToken(String token) {
	  boolean response = false;
	  try {
		  Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
		  response = true;
	  } catch (JwtException | IllegalArgumentException e) {
		  response = false;
	  }
	  return response;
  }

}