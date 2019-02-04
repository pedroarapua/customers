package br.com.magazineluiza.v1.customers.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

import br.com.magazineluiza.v1.customers.filter.JwtTokenFilter;
import br.com.magazineluiza.v1.customers.filter.RequestHeaderFilter;
import br.com.magazineluiza.v1.customers.filter.RequestResponseLoggingFilter;
import br.com.magazineluiza.v1.customers.filter.ResponseHeaderFilter;
import br.com.magazineluiza.v1.customers.security.JwtEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JwtConfig extends WebSecurityConfigurerAdapter {

	private final JwtTokenFilter authenticationTokenFilter;
	private final JwtEntryPoint jwtEntryPoint;
	private final RequestHeaderFilter requestHeaderFilter;
	private final ResponseHeaderFilter responseHeaderFilter;
	private final RequestResponseLoggingFilter requestResponseLoggingFilter;

	public JwtConfig(
		JwtTokenFilter authenticationTokenFilter,
		JwtEntryPoint jwtEntryPoint,
		RequestHeaderFilter requestHeaderFilter,
		ResponseHeaderFilter responseHeaderFilter,
		RequestResponseLoggingFilter requestResponseLoggingFilter) {
		this.authenticationTokenFilter = authenticationTokenFilter;
		this.jwtEntryPoint = jwtEntryPoint;
		this.requestHeaderFilter = requestHeaderFilter;
		this.responseHeaderFilter = responseHeaderFilter;
		this.requestResponseLoggingFilter = requestResponseLoggingFilter;
	}

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

    //http.addFilterBefore(this.requestHeaderFilter, SecurityContextHolderAwareRequestFilter.class);
    http.addFilterAfter(this.authenticationTokenFilter, SecurityContextHolderAwareRequestFilter.class);
//    http.addFilterAfter(this.responseHeaderFilter, JwtTokenFilter.class);
//    http.addFilterAfter(this.requestResponseLoggingFilter, ResponseHeaderFilter.class);
    
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