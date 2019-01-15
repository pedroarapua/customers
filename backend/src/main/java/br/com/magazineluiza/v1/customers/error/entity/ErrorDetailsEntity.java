package br.com.magazineluiza.v1.customers.error.entity;

import java.util.Date;

public class ErrorDetailsEntity {
	private Date timestamp;
	private String message;
	private String details;

	public ErrorDetailsEntity(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}
}
