package br.com.magazineluiza.v1.customers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import brave.http.HttpTracing;
import brave.spring.webmvc.TracingHandlerInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired(required=false)
    HttpTracing httpTracing;

    @Override
	public void addInterceptors(InterceptorRegistry registry) {
		if(httpTracing != null) {
			registry.addInterceptor(TracingHandlerInterceptor.create(httpTracing));
		}
	}
}
