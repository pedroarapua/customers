package br.com.magazineluiza.v1.customers.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AMSAuthenticationTokenFilter authenticationTokenFilter;
	@Autowired
	private JwtAuthenticationEntryPoint jwtEntryPoint;

//	public WebSecurityConfig(
//		AMSAuthenticationTokenFilter authenticationTokenFilter) {
//		this.authenticationTokenFilter = authenticationTokenFilter;
//	}

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // Disable CSRF (cross site request forgery)
    http.csrf().disable();

    // No session will be created or used by spring security
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // Entry points
    http.authorizeRequests()//
      .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
      // Disallow everything else..
      .anyRequest().authenticated();

    // If a user try to access a resource without having enough permissions
    http.exceptionHandling().authenticationEntryPoint(jwtEntryPoint);

    http.addFilterAfter(authenticationTokenFilter, SecurityContextHolderAwareRequestFilter.class);

//    http.formLogin()
//      .disable();

//     Optional, if you want to test the API from a browser
//    http.httpBasic();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    // Allow swagger to be accessed without authentication
    web.ignoring()
    	.antMatchers("/auth/signin")
    	.antMatchers("/v2/api-docs")//
    	.antMatchers("/swagger-resources/**")//
    	.antMatchers("/swagger-ui.html")//
    	.antMatchers("/swagger.json")//
    	.antMatchers("/configuration/**")//
    	.antMatchers("/webjars/**")//
    	.antMatchers("/actuator/**")//
    	.antMatchers("/public");
  }
}