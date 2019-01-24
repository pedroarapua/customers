package br.com.magazineluiza.v1.customers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.magazineluiza.v1.customers.entity.SigninEntity;
import br.com.magazineluiza.v1.customers.security.JwtTokenProvider;

@Service
public class AuthService {

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  public SigninEntity signin() {
	  return new SigninEntity(jwtTokenProvider.createToken());
  }
}
