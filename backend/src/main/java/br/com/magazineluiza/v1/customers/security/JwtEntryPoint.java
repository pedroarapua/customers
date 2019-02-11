package br.com.magazineluiza.v1.customers.security;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.magazineluiza.v1.customers.filter.RequestResponseLoggingFilter;
import br.com.magazineluiza.v1.customers.filter.ResponseHeaderFilter;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {
	@Autowired
	private ResponseHeaderFilter responseHeaderFilter;
	@Autowired
	private RequestResponseLoggingFilter requestResponseLoggingFilter;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException authException)
			throws IOException, ServletException {
		
    	
    	final Map<String, Object> mapBodyException = new HashMap<>() ;
    	
    	mapBodyException.put("timestamp", "invalid token");
    	mapBodyException.put("status", 401);
    	mapBodyException.put("error", "Unauthorized");
    	mapBodyException.put("message", "invalid token");
        mapBodyException.put("details", authException.getMessage());
        mapBodyException.put("path", request.getServletPath());
       
    	this.responseHeaderFilter.addHeaderResponse(request, response);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final ObjectMapper mapper = new ObjectMapper() ;
        this.requestResponseLoggingFilter.info(request, response, mapper.writeValueAsString(mapBodyException));
        
        mapper.writeValue(response.getOutputStream(), mapBodyException);
        
        
	}

}
