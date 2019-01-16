package br.com.magazineluiza.v1.customers.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

public class JwtTokenFilterConfigurer extends
  SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final AMSAuthenticationTokenFilter authenticationTokenFilter;

  public JwtTokenFilterConfigurer(
    AMSAuthenticationTokenFilter authenticationTokenFilter) {
    this.authenticationTokenFilter = authenticationTokenFilter;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.addFilterAfter(authenticationTokenFilter, SecurityContextHolderAwareRequestFilter.class);
  }
}