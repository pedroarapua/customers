package br.com.magazineluiza.v1.customers.entity;

import java.io.Serializable;

import javax.persistence.Column;

public class BranchEntity implements Serializable {
	private static final long serialVersionUID = 2392471201090556435L;

	@Column(name = "CODFILCAD")
	private Long id;

	public BranchEntity() {
	}

	public BranchEntity(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
