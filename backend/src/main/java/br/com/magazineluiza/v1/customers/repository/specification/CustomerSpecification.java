package br.com.magazineluiza.v1.customers.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import br.com.magazineluiza.v1.customers.entity.CustomerEntity;

public class CustomerSpecification {
	
	public Specification<CustomerEntity> hasId(Long id) {
        return (customer, cq, cb) -> cb.equal(customer.get("id"), id);
    }
	
	public Specification<CustomerEntity> hasCpf(String cpf) {
        return (customer, cq, cb) -> cb.equal(customer.get("cgccpf"), cpf);
    }
	
	public Specification<CustomerEntity> hasCnpj(String cnpj) {
        return (customer, cq, cb) -> cb.equal(customer.get("cgccpf"), cnpj);
    }
}