package br.com.magazineluiza.v1.customers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import br.com.magazineluiza.v1.customers.entity.CustomerEntity;
import br.com.magazineluiza.v1.customers.repository.CustomerRepository;
import br.com.magazineluiza.v1.customers.repository.specification.GenericSpecificationsBuilder;
import br.com.magazineluiza.v1.customers.util.repository.SearchOperation;
import br.com.magazineluiza.v1.customers.util.repository.SpecSearchCriteria;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository repository;
    
    public Optional<CustomerEntity> findById(Long id) {
        return this.repository.findById(id);
    }
    
    public List<CustomerEntity> filter(Long id, String cpf, String cnpj, Integer offset, Integer limit) {
    	GenericSpecificationsBuilder<CustomerEntity> builder = new GenericSpecificationsBuilder<CustomerEntity>();
    	if(cpf != null && !cpf.isEmpty()) {
    		builder.with(new SpecSearchCriteria("cpf", SearchOperation.EQUALITY, cpf));
    	}
    	else if(cnpj != null && !cnpj.isEmpty()) {
    		builder.with(new SpecSearchCriteria("cpf", SearchOperation.EQUALITY, cnpj));
    	}
    	else if(id != null) {
    		builder.with(new SpecSearchCriteria("id", SearchOperation.EQUALITY, id));
    	}
    	
    	Pageable pageable = PageRequest.of(offset, limit, Direction.ASC, "name", "id");
    	Page<CustomerEntity> page = this.repository.findAll(builder.build(), pageable);
    	return page.getContent();
    }
}
