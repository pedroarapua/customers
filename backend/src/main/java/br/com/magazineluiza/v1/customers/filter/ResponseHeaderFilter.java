package br.com.magazineluiza.v1.customers.filter;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import brave.Tracer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by JavaDeveloperZone on 16-12-2017.
 */
//@Order(value = Ordered.LOWEST_PRECEDENCE - 1)
@Accessors(fluent = true)
@NoArgsConstructor
public class ResponseHeaderFilter implements Filter {
	
	@Autowired
	private Tracer tracer;
	
	@Value("${build.version}")
	@Getter @Setter private String version;
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
                                                                                              IOException,ServletException {
    	chain.doFilter(request, response);
    	this.addHeaderResponse(request, response);
    }
    
    public void addHeaderResponse(ServletRequest request, ServletResponse response) {
    	
    	HttpServletResponse httpServletResponse=(HttpServletResponse)response;
    	long startTime = (Long) request.getAttribute("startTime");
    	long diff = Instant.now().toEpochMilli() - startTime;
    	BigDecimal httpLatencySeconds = BigDecimal.valueOf(((diff) / 1000.0));
    	
    	httpServletResponse.setContentType("application/json");
    	final String spanId = this.tracer.currentSpan().context().spanIdString();
    	final String traceId = this.tracer.currentSpan().context().traceIdString();
       	httpServletResponse.addHeader("x-request-id", spanId);
       	httpServletResponse.addHeader("x-trace-id", traceId);
    	httpServletResponse.addHeader("x-response-time", Double.toString(httpLatencySeconds.doubleValue()));
    	httpServletResponse.addHeader("x-api-version", version);
    }
}