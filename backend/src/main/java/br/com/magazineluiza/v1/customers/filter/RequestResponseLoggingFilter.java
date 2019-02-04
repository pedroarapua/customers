package br.com.magazineluiza.v1.customers.filter;

import static java.nio.charset.StandardCharsets.UTF_8;
import static net.logstash.logback.argument.StructuredArguments.entries;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

//@Order(value = Ordered.LOWEST_PRECEDENCE)
@Accessors(fluent = true)
@NoArgsConstructor
@Getter @Setter
public class RequestResponseLoggingFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);
	
	private static final String KIND = "target";
	private static final String LOGTYPE= "json";
	private String environment;
	private String appName;
	private String teamName;
	private String version;
    private String token;
	
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		ContentCachingRequestWrapper requestToCache = new ContentCachingRequestWrapper(req);
		ContentCachingResponseWrapper responseToCache = new ContentCachingResponseWrapper(res);
		
        filterChain.doFilter(requestToCache, responseToCache);
        try {
        	String bodyRequest = this.getRequestData(requestToCache);
        	String bodyResponse = this.getResponseData(responseToCache);
        	this.info(requestToCache, responseToCache, bodyRequest, bodyResponse);
        	responseToCache.copyBodyToResponse();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
    }
	
	private Map<String, String> getHeadersInfoRequest(HttpServletRequest request) {

        Map<String, String> map = new HashMap<>();

        Enumeration <String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }
    
    private Map<String, String> getHeadersInfoResponse(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();

        Collection <String> headerNames = response.getHeaderNames();
        for(String header : headerNames) {
        	String value = response.getHeader(header);
        	map.put(header, value);
        }
        
        return map;
    }
    
    public String getRequestData(ContentCachingRequestWrapper request) throws IOException {
    	return IOUtils
    			.toString(request.getInputStream(),UTF_8)
    			.replaceAll("\n", "")
    			.replaceAll("\t", "");
    }
    
    public String getResponseData(ContentCachingResponseWrapper response) throws IOException {
    	return IOUtils.toString(response.getContentInputStream(),UTF_8);
    }
    
    public void info(HttpServletRequest request, HttpServletResponse response, String bodyResponse) throws UnknownHostException, IOException {
    	ContentCachingRequestWrapper requestToCache = new ContentCachingRequestWrapper(request);
    	ContentCachingResponseWrapper responseToCache = new ContentCachingResponseWrapper(response);
        String bodyRequest = this.getRequestData(requestToCache);
    	this.info(requestToCache, responseToCache,  bodyRequest, bodyResponse);
    }
    
    public void info(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, String bodyRequest, String bodyResponse) throws UnknownHostException, IOException {
    	String httpProtocol = request.getScheme();
    	String hostName = request.getServerName();
    	Integer port = request.getServerPort();
    	Integer statusCode = response.getStatus();
    	String httpMethod = request.getMethod();
    	BigDecimal httpLatencySeconds = BigDecimal.ZERO;
    	
    	if(response.getHeader("X-Response-Time") != null) {
    		httpLatencySeconds = new BigDecimal(response.getHeader("X-Response-Time"));
    	}
    	
    	String httpHost = InetAddress.getLocalHost().getHostAddress() + ":" + port;
    	String httpPath = request.getServletPath();
    	String httpUri = request.getRequestURI();
    	String httpUrl = httpProtocol + "://"
    		    + httpHost
    		    + httpUri;
    	
    	MDC.remove("logger");
    	MDC.remove("caller");
    	
    	MDC.remove("spanId");
    	MDC.remove("traceId");
    	MDC.remove("thread");
    	MDC.remove("spanExportable");
    	MDC.remove("X-B3-TraceId");
    	MDC.remove("X-B3-SpanId");
    	MDC.remove("X-Span-Export");
    	
    	MDC.put("name", this.appName());
    	MDC.put("app", this.appName());
    	MDC.put("type", LOGTYPE);
    	MDC.put("kind", KIND);
    	MDC.put("env", this.environment());
    	MDC.put("team", this.teamName());
    	MDC.put("hostname", hostName);
    	MDC.put("http.host", httpHost);
    	
    	if(httpLatencySeconds.compareTo(BigDecimal.ZERO) != 0) {
    		MDC.put("http.latency_seconds", Double.toString(httpLatencySeconds.doubleValue()));
    	}
    	
    	final ObjectMapper mapper = new ObjectMapper();
    	
    	MDC.put("http.method", httpMethod);
    	MDC.put("http.path", httpPath);
    	MDC.put("http.protocol", httpProtocol);
    	MDC.put("http.uri", httpUri);
    	MDC.put("http.url", httpUrl);
    	MDC.put("http.request_header", mapper.writeValueAsString(this.getHeadersInfoRequest(request)));
    	MDC.put("http.request_body", bodyRequest);
    	MDC.put("http.response_header", mapper.writeValueAsString(this.getHeadersInfoResponse(response)));
    	MDC.put("http.response_body", bodyResponse);
    	MDC.put("http.status_code", statusCode.toString());
    	MDC.put("peer.hostname", hostName);
    	MDC.put("peer.service", this.appName());
    	MDC.put("version", this.version());
    	MDC.put("bztoken", this.token());
    	
    	if(statusCode >= 500) {
    		MDC.put("message", "request error");
    		logger.error("request error");
    	}
    	else {
    		MDC.put("message", "request completed");
    		logger.info("request completed");
    	}
    }

}
