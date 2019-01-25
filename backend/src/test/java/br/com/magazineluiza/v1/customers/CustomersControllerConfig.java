package br.com.magazineluiza.v1.customers;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import brave.Tracer;

import br.com.magazineluiza.v1.customers.security.JwtAuthenticationEntryPoint;
import br.com.magazineluiza.v1.customers.security.JwtTokenProvider;

@Profile("test")
@Configuration
public class CustomersControllerConfig {
	@MockBean
	private JwtTokenProvider jwtTokenProvide;
	@MockBean
	private JwtAuthenticationEntryPoint entryPoint;
	@MockBean
	private Tracer trace;
}
