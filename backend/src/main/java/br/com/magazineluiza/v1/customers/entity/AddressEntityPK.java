package br.com.magazineluiza.v1.customers.entity;

import java.io.Serializable;
import javax.persistence.Column;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class AddressEntityPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "CODEND")
	private Integer id;

	@Column(name = "CODCLI")
	private Long customerId;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		else if (obj == null) {
			return false;
		}
		else if (getClass() != obj.getClass()) {
			return false;
		}
		
		AddressEntityPK other = (AddressEntityPK) obj;
		if (customerId == null) {
			if (other.customerId != null) {
				return false;
			}
		} else if (!customerId.equals(other.customerId)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
}
