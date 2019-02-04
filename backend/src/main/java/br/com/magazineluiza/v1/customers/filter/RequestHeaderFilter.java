package br.com.magazineluiza.v1.customers.filter;

import java.io.IOException;
import java.time.Instant;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Created by JavaDeveloperZone on 16-12-2017.
 */
//@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class RequestHeaderFilter implements Filter {
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
                                                                                              IOException,ServletException {
        long startTime = Instant.now().toEpochMilli();
  	  	request.setAttribute("startTime", startTime);
		
        chain.doFilter(request, response);      // continue execution of other filter chain.
    }
}