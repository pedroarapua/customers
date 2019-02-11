package br.com.magazineluiza.v1.customers.error.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @AllArgsConstructor
public class ErrorDetailsEntity {
	
	private Date timestamp;
	private String message;
	private String path;
	private String error;
	private Integer status;

}
