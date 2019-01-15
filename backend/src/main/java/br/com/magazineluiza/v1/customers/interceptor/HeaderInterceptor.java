package br.com.magazineluiza.v1.customers.interceptor;

import java.math.BigDecimal;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class HeaderInterceptor extends HandlerInterceptorAdapter  {
	
//	private static final String TRACE_ID = "trace-id";
//    private final BeanFactory beanFactory;
//    private Tracer tracer;
    
	@Value("${build.version}")
	private String version;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

	  long startTime = Instant.now().toEpochMilli();
	  request.setAttribute("startTime", startTime);
	  return true;
	}
	
	@Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
		
		long startTime = (Long) request.getAttribute("startTime");
    	BigDecimal httpLatencySeconds = new BigDecimal(((Instant.now().toEpochMilli() - startTime) / 1000.0));
    	
    	response.addHeader("X-Api-Version", version);
		response.addHeader("X-Response-Time", Double.toString(httpLatencySeconds.doubleValue()));
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    } 

}
