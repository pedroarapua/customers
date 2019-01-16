package br.com.magazineluiza.v1.customers.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "token" })
public class SigninEntity {
	
	private String token;
	
	public SigninEntity(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return this.token;
	}
}
