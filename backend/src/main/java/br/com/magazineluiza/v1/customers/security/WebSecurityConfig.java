package br.com.magazineluiza.v1.customers.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtTokenProvider jwtTokenProvider;
  @Autowired
  private JwtAuthenticationEntryPoint unauthorizedHandler;
  
  

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // No session will be created or used by spring security
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    
    // Entry points
    http
		.authorizeRequests()//
		.antMatchers("/auth/signin").permitAll()
		// Disallow everything else..
        .anyRequest().authenticated()
//        .and()
//	        .httpBasic()
//	    	.authenticationEntryPoint(unauthorizedHandler)
        .and()
	    	// Apply JWT
	    	.apply(new JwtTokenFilterConfigurer(jwtTokenProvider))
	     // Disable CSRF (cross site request forgery)
        .and()
    		.csrf()
    		.disable();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    // Allow swagger to be accessed without authentication
    web.ignoring().antMatchers("/v2/api-docs")//
    	.antMatchers("/swagger-resources/**")//
        .antMatchers("/swagger-ui.html")//
        .antMatchers("/configuration/**")//
        .antMatchers("/webjars/**")//
        .antMatchers("/public")
        .antMatchers("/csrf");
        
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }
  
}