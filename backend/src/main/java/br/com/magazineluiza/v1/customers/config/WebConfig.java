package br.com.magazineluiza.v1.customers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.magazineluiza.v1.customers.filter.RequestResponseLoggingFilter;
import brave.http.HttpTracing;
import zipkin2.Span;
import zipkin2.reporter.Reporter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
	public void addInterceptors(InterceptorRegistry registry) {
	}
    
    @Bean
	@ConditionalOnProperty(value = "logging.zipkin.enabled", havingValue = "true")
	public Reporter<Span> spanReporter() {
		return Reporter.CONSOLE;
	}
    
    
}
