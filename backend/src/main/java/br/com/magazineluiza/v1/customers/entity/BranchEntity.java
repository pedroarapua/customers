package br.com.magazineluiza.v1.customers.entity;

import java.io.Serializable;

import javax.persistence.Column;

public class BranchEntity implements Serializable {
	private static final long serialVersionUID = 2392471201090556435L;

	@Column(name = "CODFILCAD")
	private Integer id;

	public BranchEntity() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
