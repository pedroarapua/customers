package br.com.magazineluiza.v1.customers.config;

import org.springframework.beans.factory.annotation.Value;
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
	
//	@Bean
//	public RequestResponseLoggingFilter logger() {
//    	RequestResponseLoggingFilter logger = new RequestResponseLoggingFilter();
//    	logger
//    		.appName(appName)
//    		.teamName(teamName)
//    		.version(version)
//    		.environment(environment);
//		
//    	return logger;
//	}
	
}
