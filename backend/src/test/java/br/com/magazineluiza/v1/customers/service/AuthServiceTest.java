package br.com.magazineluiza.v1.customers.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.magazineluiza.v1.customers.entity.SigninEntity;
import br.com.magazineluiza.v1.customers.security.JwtTokenProvider;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {
	@InjectMocks
	private AuthService service;
	@Mock
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
