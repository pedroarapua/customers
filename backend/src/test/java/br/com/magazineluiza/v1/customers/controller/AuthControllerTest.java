package br.com.magazineluiza.v1.customers.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.magazineluiza.v1.customers.CustomersControllerConfig;
import br.com.magazineluiza.v1.customers.builder.AuthSigninBuilder;
import br.com.magazineluiza.v1.customers.entity.SigninEntity;
import br.com.magazineluiza.v1.customers.service.AuthService;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
@Import({CustomersControllerConfig.class})
@ActiveProfiles("test")
public class AuthControllerTest {
	
	@MockBean
	private AuthService service;
	@Autowired
	private MockMvc mockMvc;
	private AuthSigninBuilder signinBuilder;
	private final String basePath = "/auth";
	
	public AuthControllerTest() {
		this.signinBuilder = new AuthSigninBuilder();
	}
	
	@Test
    public void whenSigninTest() throws Exception {
		SigninEntity signinEntity = this.signinBuilder.signin();
		
		given(service.signin()).willReturn(signinEntity);
		
		this.mockMvc
		 	.perform(post(basePath + "/signin").with(csrf()).contentType(APPLICATION_JSON))
		 		.andExpect(status().isOk())
		 		.andExpect(content().contentType(APPLICATION_JSON))
		 		.andExpect(jsonPath("$.token", is(signinEntity.getToken())));
		
		verify(service, VerificationModeFactory.times(1)).signin();
		reset(service);
    }

}
