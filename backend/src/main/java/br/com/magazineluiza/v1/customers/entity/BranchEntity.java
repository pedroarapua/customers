package br.com.magazineluiza.v1.customers.entity;

import javax.persistence.Column;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class BranchEntity {
	
	@Column(name = "CODFILCAD")
	private Integer id;

}
