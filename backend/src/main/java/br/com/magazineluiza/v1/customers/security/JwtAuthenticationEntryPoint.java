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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.magazineluiza.v1.customers.filter.ResponseHeaderFilter;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	@Autowired
	ResponseHeaderFilter responseHeaderFilter;
	
    @Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException authException)
			throws IOException, ServletException {
		
    	
    	final Map<String, Object> mapBodyException = new HashMap<>() ;
    	
    	mapBodyException.put("message", "invalid token");
        mapBodyException.put("details", authException.getMessage());
        mapBodyException.put("timestamp", new Date());

        responseHeaderFilter.addHeaderResponse(request, response);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final ObjectMapper mapper = new ObjectMapper() ;
        mapper.writeValue(response.getOutputStream(), mapBodyException);
	}

}
