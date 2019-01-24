package br.com.magazineluiza.v1.customers.builder;

import br.com.magazineluiza.v1.customers.entity.SigninEntity;
import br.com.magazineluiza.v1.customers.generator.SigninGenerator;

public class AuthSigninBuilder {
	private final SigninGenerator signinGenerator;
	
	public AuthSigninBuilder() {
		this.signinGenerator = new SigninGenerator();
	}
	
	public SigninEntity signin() {
		return this.signinGenerator.schema().get();
	}
}
