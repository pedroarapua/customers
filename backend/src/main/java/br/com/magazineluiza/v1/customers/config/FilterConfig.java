package br.com.magazineluiza.v1.customers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import br.com.magazineluiza.v1.customers.filter.JwtTokenFilter;
import br.com.magazineluiza.v1.customers.filter.RequestHeaderFilter;
import br.com.magazineluiza.v1.customers.filter.RequestResponseLoggingFilter;
import br.com.magazineluiza.v1.customers.filter.ResponseHeaderFilter;

@Configuration
public class FilterConfig {
	@Value("${env}")
	private String environment;
	
	@Value("${spring.application.name}")
	private String appName;
	
	@Value("${team.name}")
	private String teamName;
	
	@Value("${build.version}")
	private String version;
	
	@Value("${logging.logstash.token:@null}")
	private String token;
	
	@Value("${logging.fluentd.enabled:false}")
	private boolean fluentdEnabled;
	
	@Value("${logging.stackdriver.enabled:false}")
	private boolean stackDriverEnabled;
	
	@Autowired
	private ResponseHeaderFilter responseHeaderFilter;
	@Autowired
	private RequestHeaderFilter requestHeaderFilter;
	@Autowired
	private RequestResponseLoggingFilter requestResponseLoggingFilter;
	@Autowired
	private JwtTokenFilter jwtTokenFilter;
	
	@Bean
	public RequestHeaderFilter requestHeaderFilter() {
		return new RequestHeaderFilter();
	}
	
	@Bean
	public FilterRegistrationBean<RequestHeaderFilter> filterRequestHeaderBean() {
		final FilterRegistrationBean<RequestHeaderFilter> registrationBean = new FilterRegistrationBean<RequestHeaderFilter>();
		
		registrationBean.setFilter(this.requestHeaderFilter);
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		registrationBean.addUrlPatterns(
				"/customers",
				"/customers/*",
				"/auth",
				"/auth/*",
				"/actuator",
				"/actuator/*"
				);
		
	  	return registrationBean;
	}
	
	@Bean
	public JwtTokenFilter jwtTokenFilter() {
		return new JwtTokenFilter();
	}
	
	@Bean
	public FilterRegistrationBean<JwtTokenFilter> jwtFilter(){
		final FilterRegistrationBean<JwtTokenFilter> registrationBean = new FilterRegistrationBean<JwtTokenFilter>();
		registrationBean.setFilter(this.jwtTokenFilter);
		registrationBean.addUrlPatterns(
				"/customers",
				"/customers/*"
				);
		// set as false to avoid multiple filter calls
		registrationBean.setEnabled(false);
		return registrationBean;
	}
	
	@Bean
	public ResponseHeaderFilter responseHeaderFilter() {
		final ResponseHeaderFilter responseHeaderFilter = new ResponseHeaderFilter();
		responseHeaderFilter.version(version);
		
		return responseHeaderFilter;
	}
	
	@Bean
	public FilterRegistrationBean<RequestResponseLoggingFilter> filterLoggerBean() {
		final FilterRegistrationBean<RequestResponseLoggingFilter> registrationBean = new FilterRegistrationBean<RequestResponseLoggingFilter>();
		
		registrationBean.setFilter(this.requestResponseLoggingFilter);
		registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE - 1);
		registrationBean.addUrlPatterns(
				"/customers",
				"/customers/*",
				"/auth",
				"/auth/*",
				"/actuator",
				"/actuator/*"
				);
		
	  	return registrationBean;
	}
	
	@Bean
	public FilterRegistrationBean<ResponseHeaderFilter> filterResponseHeaderBean() {
		final FilterRegistrationBean<ResponseHeaderFilter> registrationBean = new FilterRegistrationBean<ResponseHeaderFilter>();
		
		registrationBean.setFilter(this.responseHeaderFilter);
		registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE);
		registrationBean.addUrlPatterns(
				"/customers",
				"/customers/*",
				"/auth",
				"/auth/*",
				"/actuator",
				"/actuator/*"
				);
		
	  	return registrationBean;
	}
	
	@Bean
	public RequestResponseLoggingFilter requestResponseLoggingFilter() {
		final RequestResponseLoggingFilter filter = new RequestResponseLoggingFilter();
		
		filter
			.appName(appName)
			.environment(environment)
			.teamName(teamName)
			.token(token)
			.stackDriverEnabled(stackDriverEnabled)
			.version(version);
		
		return filter;
	}
}
