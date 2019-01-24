package br.com.magazineluiza.v1.customers.config;

import java.net.InetAddress;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import io.sentry.Sentry;
import io.sentry.SentryClient;

@Configuration
@ConditionalOnProperty(name = "notification.enabled", havingValue = "true")
public class NotificationConfig {
	@Value("${notification.sentry.dsn:#{null}}")
	private Optional<String> dsn;
	@Value("${build.version}")
	private String version;
	@Value("${env}")
	private String environment;
	
	@Bean
	@ConditionalOnProperty(name = "notification.sentry.enabled", havingValue = "true")
	public HandlerExceptionResolver sentryExceptionResolver() throws Exception {
		if(!dsn.isPresent()) {
			throw new Exception("DSN is required to enable sentry");
		}
		
		SentryClient client = Sentry.init(dsn.get());
		client.addTag("release", version);
		client.addTag("environment", environment);
		client.addTag("servername", InetAddress.getLocalHost().getHostName());
		client.addTag("serverip", InetAddress.getLocalHost().toString().split("/")[1]);
		return new io.sentry.spring.SentryExceptionResolver();
	}
	
	@Bean
	@ConditionalOnProperty(name = "notification.sentry.enabled", havingValue = "true")
	public ServletContextInitializer sentryServletContextInitializer() {
	    return new io.sentry.spring.SentryServletContextInitializer();
	}

}
