package br.com.magazineluiza.v1.customers.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.magazineluiza.v1.customers.entity.SigninEntity;
import br.com.magazineluiza.v1.customers.security.JwtTokenProvider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class AuthServiceTest {
	@Configuration
	static class AuthServiceTestContextConfiguration {
		@Bean
		public AuthService service() {
			return new AuthService();
		}
		
		@Bean
		public JwtTokenProvider jwtTokenProvider() {
			return Mockito.mock(JwtTokenProvider.class);
		}
	}
	
	@Autowired
	private AuthService service;
	@Autowired
	private JwtTokenProvider jwtTokenProvider; 
	
	@Test
    public void whenSigninTest() throws Exception {
		String token = "123";
		given(jwtTokenProvider.createToken()).willReturn(token);
		
		SigninEntity signinEntity = this.service.signin();
		
		assertNotNull(signinEntity);
		assertEquals(token, signinEntity.getToken()); 
    }
}
