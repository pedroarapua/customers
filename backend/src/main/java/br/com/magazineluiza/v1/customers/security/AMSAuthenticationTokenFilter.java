package br.com.magazineluiza.v1.customers.security;


//import com.inner.satisfaction.backend.authentication.token.AuthenticationTokenService;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class AMSAuthenticationTokenFilter extends GenericFilterBean {

  private JwtTokenProvider jwtTokenProvider;

  public AMSAuthenticationTokenFilter(
		  JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
    FilterChain filterChain) throws IOException, ServletException {

    Optional<String> token;
    HttpServletRequest req = (HttpServletRequest) servletRequest;
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      token = Optional.of(bearerToken.substring(7));
    } else {
      token = Optional.empty();
    }
    
//    String token = jwtTokenProvider.resolveToken(req);
    try {
      if (jwtTokenProvider.validateToken(token.get())) {
    	  SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(token, null, Collections.emptyList()));

      }
    } catch (AccessDeniedException ex) {
      //this is very important, since it guarantees the user is not authenticated at all
      //SecurityContextHolder.clearContext();
    }


    
//    Optional<AuthenticationToken> authenticationToken = token
//      .map(authenticationTokenService::findByToken)
//      .filter(Optional::isPresent)
//      .map(Optional::get);

    if (token.isPresent()) {
      //SecurityContextHolder.getContext().setAuthentication(authenticationToken.get());
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }
}