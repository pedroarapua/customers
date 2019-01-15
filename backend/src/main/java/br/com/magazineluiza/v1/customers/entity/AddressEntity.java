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

@Entity
@Table(schema = "GEMCO", name = "CAD_ENDCLI")
@JsonPropertyOrder({ "id", "street", "number", "complement", "district", "city", "state", "zipCode" })
public class AddressEntity {
	@EmbeddedId
	@JsonIgnore
	private AddressEntityPK pk;

	@Transient
	private Long id;

	@JsonIgnore
	@Column(name = "CODLOGRAD")
	private String streetType;

	@Column(name = "ENDERECO")
	private String street;

	@Column(name = "NUMERO")
	private Long number;

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

	public AddressEntity() {
		super();
	}

	@JsonProperty
	public Long getId() {
		this.id = this.pk.getId();
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreetType() {
		return streetType;
	}

	public void setStreetType(String streetType) {
		this.streetType = streetType;
	}

	@JsonProperty
	public String getStreet() {
		this.street = this.streetType + " " + this.street;
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}
}
