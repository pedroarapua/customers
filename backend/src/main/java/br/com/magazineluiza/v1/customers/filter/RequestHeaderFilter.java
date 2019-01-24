package br.com.magazineluiza.v1.customers.filter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import java.io.IOException;
import java.time.Instant;

/**
 * Created by JavaDeveloperZone on 16-12-2017.
 */
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class RequestHeaderFilter implements Filter {
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
                                                                                              IOException,ServletException {
        long startTime = Instant.now().toEpochMilli();
  	  	request.setAttribute("startTime", startTime);
		
        chain.doFilter(request, response);      // continue execution of other filter chain.
    }
}