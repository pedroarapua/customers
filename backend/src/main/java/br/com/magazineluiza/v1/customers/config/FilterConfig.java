package br.com.magazineluiza.v1.customers.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

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
	
}
