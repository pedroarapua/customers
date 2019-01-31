package br.com.magazineluiza.v1.customers.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.magazineluiza.v1.customers.builder.AuthSigninBuilder;
import br.com.magazineluiza.v1.customers.entity.SigninEntity;
import br.com.magazineluiza.v1.customers.service.AuthService;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class AuthControllerTest {
	
	private MockMvc mockMvc;
	private final String basePath = "/auth";
	private final static MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
	@Mock
	private AuthService service;
	@InjectMocks
	private AuthController controller;
	private AuthSigninBuilder builder;
	
	@Before
    public void setUp() {
		this.builder = new AuthSigninBuilder();
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
    }
	
	@Test
    public void whenSigninTest() throws Exception {
		SigninEntity signinEntity = this.builder.signin();
		
		given(service.signin()).willReturn(signinEntity);
		
		this.mockMvc
		 	.perform(post(basePath + "/signin").with(csrf()).contentType(APPLICATION_JSON))
		 		.andExpect(status().isOk())
		 		.andExpect(content().contentType(MEDIA_TYPE_JSON_UTF8))
		 		.andExpect(jsonPath("$.token", is(signinEntity.getToken())));
		
		verify(service, VerificationModeFactory.times(1)).signin();
		reset(service);
    }

}
