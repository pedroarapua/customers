package br.com.magazineluiza.v1.customers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import br.com.magazineluiza.v1.customers.entity.CustomerEntity;
import br.com.magazineluiza.v1.customers.repository.CustomerRepository;


@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public List<CustomerEntity> findAll() {
        return this.customerRepository.findAll();
    }
    
    public Optional<CustomerEntity> findById(Long id) {
        return this.customerRepository.findById(id);
    }
    
    public Page<CustomerEntity> filter(Long id, String cpf, String cnpj, Integer offset, Integer limit) {
    	CustomerEntity customerFilter = new CustomerEntity();
    	if(cpf != null && !cpf.isEmpty()) {
    		customerFilter.setCpf(cpf);
    	}
    	else if(cnpj != null && !cnpj.isEmpty()) {
    		customerFilter.setCpf(cnpj);
    	}
    	else if(id != null && id > 0) {
    		customerFilter.setId(id);
    	}
    	
    	Pageable pageable = new PageRequest(offset, limit, Direction.ASC, "name", "id");
    	return this.customerRepository.findAll(
    			Example.of(customerFilter),
    			pageable);
    }
}
