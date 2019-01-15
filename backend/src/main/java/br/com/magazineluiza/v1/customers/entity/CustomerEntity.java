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

@Entity
@Table(schema = "GEMCO", name = "CAD_CLIENTE")
@JsonPropertyOrder({ "id", "digit", "name", "cpf", "cnpj", "rg", "branch", "address" })
public class CustomerEntity {
	@Id
    @Column(name = "CODCLI")
    private Long id;

    @Column(name = "DIGCLI")
    private Long digit;

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

    public CustomerEntity() {
        super();
    }

    public CustomerEntity(Long id, Long digit, String name, String cpf, String natJur, String rg) {
        super();
        this.id = id;
        this.digit = digit;
        this.name = name;
        this.cpf = cpf;
        this.natJur = natJur;
        this.rg = rg;
        this.branch = branch;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDigit() {
        return digit;
    }

    public void setDigit(Long digit) {
        this.digit = digit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getCpf() {
        // Condicional pois no gemco a COLUNA chama CGCCPF que armazena CPNJ/CPF
//        if (!"J".equalsIgnoreCase(this.natJur) && this.cpf != null) {
//            return CNPJCPFUtils.formatCPF(cpf);
//        }
//        return null;
    	return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @JsonProperty
    public String getCnpj() {
        // Condicional pois no gemco a COLUNA chama CGCCPF que armazena CPNJ/CPF
//        if ("J".equalsIgnoreCase(this.natJur) && this.cpf != null) {
//            return CNPJCPFUtils.formatCNPJ(this.cpf);
//        }
//        return null;
    	return cpf;
    }

    public void setCnpj(String cnpj) {
    	this.cnpj = cnpj;
    }

    public String getNatJur() {
        return natJur;
    }

    public void setNatJur(String natJur) {
        this.natJur = natJur;
    }

    public BranchEntity getBranch() {
        return branch;
    }

    public void setBranch(BranchEntity branch) {
        this.branch = branch;
    }

    public List<AddressEntity> getAddress() {
        return address;
    }

    public void setAddress(List<AddressEntity> address) {
        this.address = address;
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
