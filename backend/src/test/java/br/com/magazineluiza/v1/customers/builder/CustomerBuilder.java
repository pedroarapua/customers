package br.com.magazineluiza.v1.customers.builder;

import java.util.List;
import java.util.Optional;

import br.com.magazineluiza.v1.customers.entity.AddressEntity;
import br.com.magazineluiza.v1.customers.entity.CustomerEntity;
import br.com.magazineluiza.v1.customers.generator.CustomerGenerator;

public class CustomerBuilder {
	private final CustomerGenerator customerGenerator;
	
	public CustomerBuilder() {
		this.customerGenerator = new CustomerGenerator();
	}
	
	public List<CustomerEntity> filter(Integer offset,
		Integer limit,
        String cpf,
        String cnpj,
        Long id) {
		
		List<CustomerEntity> lst = this.customerGenerator.schema().list(limit).get();
		if(cpf != null) {
			lst.get(0).setCpf(cpf);
		} else if(cnpj != null) {
			lst.get(0).setCnpj(cnpj);
		} else if(id != null) {
			lst.get(0).setId(id);
			for(CustomerEntity customer : lst) {
				for(AddressEntity address : customer.getAddress()) {
					address.getPk().setCustomerId(customer.getId());
				}
			}
		}
		
		return lst;
	}
	
	public Optional<CustomerEntity> findById() {
		return Optional.of(this.customerGenerator.schema().get());
	}
}
