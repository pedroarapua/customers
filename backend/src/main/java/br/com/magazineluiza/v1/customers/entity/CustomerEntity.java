package br.com.magazineluiza.v1.customers.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "GEMCO", name = "CAD_CLIENTE")
@JsonPropertyOrder({ "id", "digit", "name", "cpf", "cnpj", "rg", "branch", "address" })
@Getter @Setter @NoArgsConstructor
public class CustomerEntity {
	@Id
    @Column(name = "CODCLI")
    private Long id;

    @Column(name = "DIGCLI")
    private Integer digit;

    @Column(name = "NOMCLI")
    private String name;

    @Column(name = "CGCCPF")
    private String cpf;

    @Transient
    private String cnpj;

    @JsonIgnore
    @Column(name = "NATJUR")
    private String natJur;

    @Column(name = "NUMDOC")
    private String rg;

    @Embedded
    private BranchEntity branch;

    @OneToMany(mappedBy = "customer")
    private List<AddressEntity> address;

    @JsonProperty
    public String getCpf() {
        // Condicional pois no gemco a COLUNA chama CGCCPF que armazena CPNJ/CPF
        if (!"J".equalsIgnoreCase(this.natJur) && this.cpf != null) {
        	return this.cpf;
        }
        return null;
    	
    }

    @JsonProperty
    public String getCnpj() {
        // Condicional pois no gemco a COLUNA chama CGCCPF que armazena CPNJ/CPF
        if ("J".equalsIgnoreCase(this.natJur) && this.cpf != null) {
        	return this.cnpj;
        }
        return null;
    	
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
                return true;
        }
        if (obj == null) {
                return false;
        }
        if (!(obj instanceof CustomerEntity)) {
                return false;
        }
        CustomerEntity other = (CustomerEntity) obj;
        if (getId() == null) {
            if (other.getId() != null) {
                    return false;
            }
        } else if (!getId().equals(other.getId())) {
            return false;
        }
        return true;
    }
}
