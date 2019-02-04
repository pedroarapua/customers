package br.com.magazineluiza.v1.customers.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import br.com.magazineluiza.v1.customers.security.JwtTokenProvider;

public class JwtTokenFilter extends GenericFilterBean {
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	private final Authentication anonymous = new UsernamePasswordAuthenticationToken("anonims", null, Collections.emptyList()); 
	
	@Value("${security.jwt.enabled}")
	private Boolean jwtEnabaled;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
		FilterChain filterChain) throws IOException, ServletException {
		
		if(jwtEnabaled) {
			Optional<String> token = jwtTokenProvider.resolveToken(servletRequest);
			if (token.isPresent() && jwtTokenProvider.validateToken(token.get())) {
				SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(token, null, Collections.emptyList()));
			}
			else {
				SecurityContextHolder.clearContext();
			}
		}
		else {
			SecurityContextHolder.getContext().setAuthentication(anonymous);
		}
		
		filterChain.doFilter(servletRequest, servletResponse);
	}
}