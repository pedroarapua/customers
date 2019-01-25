package br.com.magazineluiza.v1.customers.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import brave.Tracer;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * Created by JavaDeveloperZone on 16-12-2017.
 */
@Component
@Order(value = Ordered.LOWEST_PRECEDENCE)
public class ResponseHeaderFilter implements Filter {
	
	@Autowired
	private Tracer tracer;
	
	@Value("${build.version}")
	private String version;
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
                                                                                              IOException,ServletException {
        this.addHeaderResponse(request, response);
    	chain.doFilter(request, response);      // continue execution of other filter chain.
    }
    
    public void addHeaderResponse(ServletRequest request, ServletResponse response) {
    	
    	HttpServletResponse httpServletResponse=(HttpServletResponse)response;
    	long startTime = (Long) request.getAttribute("startTime");
    	BigDecimal httpLatencySeconds = BigDecimal.valueOf(((Instant.now().toEpochMilli() - startTime) / 1000.0));
    	
    	httpServletResponse.setContentType("application/json");
       	httpServletResponse.addHeader("X-Api-Version", version);
       	final String spanId = this.tracer.currentSpan().context().spanIdString();
    	final String traceId = this.tracer.currentSpan().context().traceIdString();
       	httpServletResponse.addHeader("X-Request-Id", spanId);
       	httpServletResponse.addHeader("X-Trace-Id", traceId);
    	httpServletResponse.addHeader("X-Response-Time", Double.toString(httpLatencySeconds.doubleValue()));
    }
}