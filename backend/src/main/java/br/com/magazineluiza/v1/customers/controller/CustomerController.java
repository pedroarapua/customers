package br.com.magazineluiza.v1.customers.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.magazineluiza.v1.customers.entity.CustomerEntity;
import br.com.magazineluiza.v1.customers.exception.ResourceNotFoundException;
import br.com.magazineluiza.v1.customers.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/customers")
@Api(value="Customer Management System")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    
    @ApiOperation(value = "Filter customer by id or cpf or cnpj", response = List.class)
    @ApiResponses(value = {
    	    @ApiResponse(code = 200, message = "Successfully retrieved list"),
    	    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    	    @ApiResponse(code = 404, message = "Customer not found")
    	})
    @GetMapping(value = "")
    public ResponseEntity<List<CustomerEntity>> findAll(
    		@RequestParam(name = "offset", defaultValue = "0") Integer offset,
            @RequestParam(name = "limit", defaultValue = "50") Integer limit,
            @RequestParam(name = "cpf", required = false) String cpf,
            @RequestParam(name = "cnpj", required = false) String cnpj,
            @RequestParam(name = "id", required = false) Long id) throws ResourceNotFoundException {
        
    	List<CustomerEntity> lstCustomers = this.customerService.filter(id, cpf, cnpj, offset, limit);
    	
    	if(lstCustomers.isEmpty()) {
    		throw new ResourceNotFoundException(
    				"Customers not found for this parameters id:: " + id +
    				" cpf:: " + cpf +
    				" cnpj:: " + cnpj +
    				" offset:: " + offset +
    				" limit:: " + limit
    				);
    	}
    	
		return ResponseEntity.status(HttpStatus.OK).body(lstCustomers);
    }

    @ApiOperation(value = "Get an customer by Id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerEntity> getById(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
    	CustomerEntity customer = this.customerService.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + id));
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }
}
