package br.com.magazineluiza.v1.customers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.magazineluiza.v1.customers.entity.SigninEntity;
import br.com.magazineluiza.v1.customers.security.JwtTokenProvider;

@Service
public class AuthService {

  public SigninEntity signin() {
	  SigninEntity signin = new SigninEntity(this.createToken()); 
	  return signin;
  }
  
  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new AccessDeniedException("Expired or invalid JWT token");
    }
  }
  
}


