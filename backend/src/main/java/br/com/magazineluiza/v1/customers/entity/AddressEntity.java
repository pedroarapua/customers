package br.com.magazineluiza.v1.customers.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "GEMCO", name = "CAD_ENDCLI")
@JsonPropertyOrder({ "id", "street", "number", "complement", "district", "city", "state", "zipCode" })
@Getter @Setter @NoArgsConstructor
public class AddressEntity {
	@EmbeddedId
	@JsonIgnore
	private AddressEntityPK pk;

	@Transient
	private Integer id;

	@JsonIgnore
	@Column(name = "CODLOGRAD")
	private String streetType;

	@Column(name = "ENDERECO")
	private String street;

	@Column(name = "NUMERO")
	private Integer number;

	@Column(name = "COMPLEMENTO")
	private String complement;

	@Column(name = "BAIRRO")
	private String district;

	@Column(name = "CIDADE")
	private String city;

	@Column(name = "ESTADO")
	private String state;

	@Column(name = "CEP")
	private String zipCode;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "CODCLI", referencedColumnName = "CODCLI", insertable = false, updatable = false)
	private CustomerEntity customer;

	@JsonProperty
	public Integer getId() {
		this.id = this.getPk().getId();
		return this.id;
	}

	@JsonProperty
	public String getStreet() {
		return this.getStreetType() + " " + this.street;
	}

}
