package br.com.magazineluiza.v1.customers.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.magazineluiza.v1.customers.filter.RequestResponseLoggingFilter;

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
	
	private String token = "teste";
	
	@Bean
    public FilterRegistrationBean<RequestResponseLoggingFilter> loggingFilter() {
        FilterRegistrationBean<RequestResponseLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        
        registrationBean.setFilter(
    		new RequestResponseLoggingFilter()
        		.setAppName(appName)
        		.setEnvironment(environment)
        		.setTeamName(teamName)
        		.setVersion(version)
        		.setToken(token));
        registrationBean.addUrlPatterns("/customers/*");

        return registrationBean;

    }

}
