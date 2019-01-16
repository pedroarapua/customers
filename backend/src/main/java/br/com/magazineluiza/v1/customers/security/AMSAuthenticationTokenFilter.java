package br.com.magazineluiza.v1.customers.security;


//import com.inner.satisfaction.backend.authentication.token.AuthenticationTokenService;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class AMSAuthenticationTokenFilter extends GenericFilterBean {

  //private AuthenticationTokenService authenticationTokenService;

//  public AMSAuthenticationTokenFilter(
//    AuthenticationTokenService authenticationTokenService) {
//    this.authenticationTokenService = authenticationTokenService;
//  }

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