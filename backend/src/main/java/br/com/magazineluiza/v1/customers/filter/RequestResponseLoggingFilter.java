package br.com.magazineluiza.v1.customers.filter;

import static net.logstash.logback.argument.StructuredArguments.entries;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import br.com.magazineluiza.v1.customers.util.WebLoggingUtil;

import org.slf4j.Logger;

@Component
public class RequestResponseLoggingFilter implements Filter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	private final String kind = "target";
	private final String logType= "json";
	private String environment;
	private String appName;
	private String teamName;
	private String version;
	private String token;
	
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
	
	@Override
    public void destroy() {
    }
	
	@Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
        throws IOException, ServletException {

        if (servletRequest instanceof HttpServletRequest
            && servletResponse instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            HttpServletRequest requestToCache = new ContentCachingRequestWrapper(request);
            HttpServletResponse responseToCache = new ContentCachingResponseWrapper(response);
            chain.doFilter(requestToCache, responseToCache);
            
            try {
				this.info(requestToCache, responseToCache);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
        } else {
            chain.doFilter(servletRequest, servletResponse);
        }
    }

    private Map<String, String> getHeadersInfoRequest(HttpServletRequest request) {

        Map<String, String> map = new HashMap<String, String>();

        Enumeration <String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }
    
    private Map<String, String> getHeadersInfoResponse(HttpServletResponse response) {
        Map<String, String> map = new HashMap<String, String>();

        Collection <String> headerNames = response.getHeaderNames();
        for(String header : headerNames) {
        	String value = response.getHeader(header);
        	map.put(header, value);
        }
        
        return map;
    }
    
    private String getRequestData(HttpServletRequest request) throws Exception {
    	String postData = WebLoggingUtil.readPayloadRequest(request);
    	return postData;
    }
    
    private String getResponseData(HttpServletResponse response) throws Exception {
    	String postData = WebLoggingUtil.readPayloadResponse(response);
    	return postData;
    }
    
    public void info(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("name", this.getAppName());
    	map.put("app", this.getAppName());
    	map.put("type", this.logType);
    	map.put("kind", this.kind);
    	map.put("env", this.getEnvironment());
    	map.put("team", this.getTeamName());
    	map.put("hostname", hostName);
    	map.put("http.host", httpHost);
    	
    	if(httpLatencySeconds.compareTo(BigDecimal.ZERO) != 0) {
    		map.put("http.latency_seconds", httpLatencySeconds.doubleValue());
    	}
    	
    	map.put("http.method", httpMethod);
    	map.put("http.path", httpPath);
    	map.put("http.protocol", httpProtocol);
    	map.put("http.uri", httpUri);
    	map.put("http.url", httpUrl);
    	map.put("http.request_header", this.getHeadersInfoRequest(request));
    	map.put("http.request_body", this.getRequestData(request));
    	map.put("http.response_header", this.getHeadersInfoResponse(response));
    	map.put("http.response_body", this.getResponseData(response));
    	map.put("http.status_code", statusCode);
    	map.put("peer.hostname", hostName);
    	map.put("peer.service", this.getAppName());
    	map.put("version", this.getVersion());
    	map.put("bztoken", this.getToken());
    	
    	if(statusCode >= 500) {
    		map.put("message", "request error");
    		logger.error("{}", entries(map));
    	}
    	else {
    		map.put("message", "request completed");
    		//logger.info("{}", entries(map));
    		logger.info("teste");
    	}
    }

    public String getEnvironment() {
		return environment;
	}

	public RequestResponseLoggingFilter setEnvironment(String environment) {
		this.environment = environment;
		return this;
	}

	public String getAppName() {
		return appName;
	}

	public RequestResponseLoggingFilter setAppName(String appName) {
		this.appName = appName;
		return this;
	}

	public String getTeamName() {
		return teamName;
	}

	public RequestResponseLoggingFilter setTeamName(String teamName) {
		this.teamName = teamName;
		return this;
	}

	public String getVersion() {
		return version;
	}

	public RequestResponseLoggingFilter setVersion(String version) {
		this.version = version;
		return this;
	}

	public String getToken() {
		return token;
	}

	public RequestResponseLoggingFilter setToken(String token) {
		this.token = token;
		return this;
	}

}
