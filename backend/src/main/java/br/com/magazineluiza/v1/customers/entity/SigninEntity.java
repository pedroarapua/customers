package br.com.magazineluiza.v1.customers.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonPropertyOrder({ "token" })
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SigninEntity {
	
	private String token;
	
}
