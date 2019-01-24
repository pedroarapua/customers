package br.com.magazineluiza.v1.customers.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.magazineluiza.v1.customers.CustomersControllerConfig;
import br.com.magazineluiza.v1.customers.builder.CustomerBuilder;
import br.com.magazineluiza.v1.customers.entity.AddressEntity;
import br.com.magazineluiza.v1.customers.entity.CustomerEntity;
import br.com.magazineluiza.v1.customers.service.CustomerService;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
@Import({CustomersControllerConfig.class})
@ActiveProfiles("test")
public class CustomerControllerTest {
	
	@MockBean
	private CustomerService service;
	@Autowired
	private MockMvc mockMvc;
	private final CustomerBuilder customerBuilder;
	private final String basePath = "/customers";
	
	public CustomerControllerTest() {
		this.customerBuilder = new CustomerBuilder();
	}
	
	@Test
	public void whenfindAllByIdTest() throws Exception {
		Integer offset = (Integer)0;
		Integer limit = (Integer)1;
		String cpf = null;
		String cnpj = null;
		Long id = Long.valueOf(1);
		String url = basePath + "?id=" + id + "&offset=" + offset + "&limit=" + limit;
		 
		List<CustomerEntity> lstCustomers = this.customerBuilder.filter(offset, limit, cpf, cnpj, id);
		given(service.filter(id, cpf, cnpj, offset, limit)).willReturn(lstCustomers);
		
		CustomerEntity customer = lstCustomers.get(0);
		AddressEntity address = customer.getAddress().get(0);
		
		this.mockMvc
		 	.perform(get(url).contentType(APPLICATION_JSON))
		 		.andExpect(status().isOk())
		 		.andExpect(content().contentType(APPLICATION_JSON))
		 		.andExpect(jsonPath("$", hasSize(1)))
		 		.andExpect(jsonPath("$[0].id", is(customer.getId().intValue())))
		 		.andExpect(jsonPath("$[0].cpf", is(customer.getCpf())))
 				.andExpect(jsonPath("$[0].digit", is(customer.getDigit())))
 				.andExpect(jsonPath("$[0].name", is(customer.getName())))
 				.andExpect(jsonPath("$[0].branch.id", is(customer.getBranch().getId())))
 				.andExpect(jsonPath("$[0].address", hasSize(2)))
 				.andExpect(jsonPath("$[0].address[0].id", is(address.getId())))
 				.andExpect(jsonPath("$[0].address[0].city", is(address.getCity())))
 				.andExpect(jsonPath("$[0].address[0].complement", is(address.getComplement())))
 				.andExpect(jsonPath("$[0].address[0].district", is(address.getDistrict())))
 				.andExpect(jsonPath("$[0].address[0].number", is(address.getNumber())))
 				.andExpect(jsonPath("$[0].address[0].state", is(address.getState())))
 				.andExpect(jsonPath("$[0].address[0].street", is(address.getStreet())))
 				.andExpect(jsonPath("$[0].address[0].zipCode", is(address.getZipCode())));
		
		verify(service, VerificationModeFactory.times(1)).filter(id, cpf, cnpj, offset, limit);
		reset(service);
    }
	
	@Test
    public void whenfindAllByCpfTest() throws Exception {
		Integer offset = (Integer)0;
		Integer limit = (Integer)1;
		String cpf = "11111111111";
		String cnpj = null;
		Long id = null;
		String url = basePath + "?cpf=" + cpf + "&offset=" + offset + "&limit=" + limit;
		 
		List<CustomerEntity> lstCustomers = this.customerBuilder.filter(offset, limit, cpf, cnpj, id);
		given(service.filter(id, cpf, cnpj, offset, limit)).willReturn(lstCustomers);
		
		CustomerEntity customer = lstCustomers.get(0);
		AddressEntity address = customer.getAddress().get(0);
		
		this.mockMvc
		 	.perform(get(url).with(csrf()).contentType(APPLICATION_JSON))
		 		.andExpect(status().isOk())
		 		.andExpect(content().contentType(APPLICATION_JSON))
		 		.andExpect(jsonPath("$", hasSize(1)))
		 		.andExpect(jsonPath("$[0].id", is(customer.getId())))
		 		.andExpect(jsonPath("$[0].cpf", is(customer.getCpf())))
 				.andExpect(jsonPath("$[0].digit", is(customer.getDigit())))
 				.andExpect(jsonPath("$[0].name", is(customer.getName())))
 				.andExpect(jsonPath("$[0].branch.id", is(customer.getBranch().getId())))
 				.andExpect(jsonPath("$[0].address", hasSize(2)))
 				.andExpect(jsonPath("$[0].address[0].id", is(address.getId())))
 				.andExpect(jsonPath("$[0].address[0].city", is(address.getCity())))
 				.andExpect(jsonPath("$[0].address[0].complement", is(address.getComplement())))
 				.andExpect(jsonPath("$[0].address[0].district", is(address.getDistrict())))
 				.andExpect(jsonPath("$[0].address[0].number", is(address.getNumber())))
 				.andExpect(jsonPath("$[0].address[0].state", is(address.getState())))
 				.andExpect(jsonPath("$[0].address[0].street", is(address.getStreet())))
 				.andExpect(jsonPath("$[0].address[0].zipCode", is(address.getZipCode())));
		
		verify(service, VerificationModeFactory.times(1)).filter(id, cpf, cnpj, offset, limit);
		reset(service);
    }
	
	@Test
    public void whenfindAllByCnpjTest() throws Exception {
		Integer offset = (Integer)0;
		Integer limit = (Integer)1;
		String cpf = null;
		String cnpj = "11111111111111";
		Long id = null;
		String url = basePath + "?cnpj=" + cnpj + "&offset=" + offset + "&limit=" + limit;
		 
		List<CustomerEntity> lstCustomers = this.customerBuilder.filter(offset, limit, cpf, cnpj, id);
		given(service.filter(id, cpf, cnpj, offset, limit)).willReturn(lstCustomers);
		
		CustomerEntity customer = lstCustomers.get(0);
		AddressEntity address = customer.getAddress().get(0);
		
		this.mockMvc
		 	.perform(get(url).with(csrf()).contentType(APPLICATION_JSON))
		 		.andExpect(status().isOk())
		 		.andExpect(content().contentType(APPLICATION_JSON))
		 		.andExpect(jsonPath("$", hasSize(1)))
		 		.andExpect(jsonPath("$[0].cnpj", is(customer.getCnpj())))
 				.andExpect(jsonPath("$[0].digit", is(customer.getDigit())))
 				.andExpect(jsonPath("$[0].id", is(customer.getId())))
 				.andExpect(jsonPath("$[0].name", is(customer.getName())))
 				.andExpect(jsonPath("$[0].branch.id", is(customer.getBranch().getId())))
 				.andExpect(jsonPath("$[0].address", hasSize(2)))
 				.andExpect(jsonPath("$[0].address[0].id", is(address.getId())))
 				.andExpect(jsonPath("$[0].address[0].city", is(address.getCity())))
 				.andExpect(jsonPath("$[0].address[0].complement", is(address.getComplement())))
 				.andExpect(jsonPath("$[0].address[0].district", is(address.getDistrict())))
 				.andExpect(jsonPath("$[0].address[0].number", is(address.getNumber())))
 				.andExpect(jsonPath("$[0].address[0].state", is(address.getState())))
 				.andExpect(jsonPath("$[0].address[0].street", is(address.getStreet())))
 				.andExpect(jsonPath("$[0].address[0].zipCode", is(address.getZipCode())));
		
		verify(service, VerificationModeFactory.times(1)).filter(id, cpf, cnpj, offset, limit);
		reset(service);
    }
	
	@Test
    public void whenfindAllNotFoundTest() throws Exception {
		Integer offset = (Integer)0;
		Integer limit = (Integer)1;
		String cpf = null;
		String cnpj = null;
		Long id = Long.valueOf(1);
		String url = basePath + "?id=" + id + "&offset=" + offset + "&limit=" + limit;
		 
		List<CustomerEntity> lstCustomers = new ArrayList<CustomerEntity>();
		given(service.filter(id, cpf, cnpj, offset, limit)).willReturn(lstCustomers);
		
		this.mockMvc
		 	.perform(get(url).with(csrf()).contentType(APPLICATION_JSON))
		 		.andExpect(status().isNotFound())
		 		.andExpect(content().contentType(APPLICATION_JSON))
		 		.andExpect(jsonPath("$", Matchers.hasKey("timestamp")))
		 		.andExpect(jsonPath("$", Matchers.hasKey("message")))
		 		.andExpect(jsonPath("$", Matchers.hasKey("details")));
		
		verify(service, VerificationModeFactory.times(1)).filter(id, cpf, cnpj, offset, limit);
		reset(service);
    }
	
	@Test
    public void whenfindByIdTest() throws Exception {
		Optional<CustomerEntity> customerOpt = customerBuilder.findById();
		CustomerEntity customer = customerOpt.get();
		AddressEntity address = customer.getAddress().get(0);
		
		String url = basePath + "/" + customer.getId();
		given(service.findById(customer.getId())).willReturn(customerOpt);
		
		this.mockMvc
	 	.perform(get(url).with(csrf()).contentType(APPLICATION_JSON))
	 		.andExpect(status().isOk())
	 		.andExpect(content().contentType(APPLICATION_JSON))
	 		.andExpect(jsonPath("$.id", is(customer.getId())))
	 		.andExpect(jsonPath("$.cpf", is(customer.getCpf())))
			.andExpect(jsonPath("$.digit", is(customer.getDigit())))
			.andExpect(jsonPath("$.name", is(customer.getName())))
			.andExpect(jsonPath("$.branch.id", is(customer.getBranch().getId())))
			.andExpect(jsonPath("$.address", hasSize(2)))
			.andExpect(jsonPath("$.address[0].id", is(address.getId())))
			.andExpect(jsonPath("$.address[0].city", is(address.getCity())))
			.andExpect(jsonPath("$.address[0].complement", is(address.getComplement())))
			.andExpect(jsonPath("$.address[0].district", is(address.getDistrict())))
			.andExpect(jsonPath("$.address[0].number", is(address.getNumber())))
			.andExpect(jsonPath("$.address[0].state", is(address.getState())))
			.andExpect(jsonPath("$.address[0].street", is(address.getStreet())))
			.andExpect(jsonPath("$.address[0].zipCode", is(address.getZipCode())));
	
		verify(service, VerificationModeFactory.times(1)).findById(customer.getId());
		reset(service);
    }
	
	@Test
    public void whenfindByIdNotFoundTest() throws Exception {
		Long id = Long.valueOf(1);
		String url = basePath + "/" + id;
		 
		Optional<CustomerEntity> customer = Optional.empty();
		given(service.findById(id)).willReturn(customer);
		
		this.mockMvc
		 	.perform(get(url).with(csrf()).contentType(APPLICATION_JSON))
		 		.andExpect(status().isNotFound())
		 		.andExpect(content().contentType(APPLICATION_JSON))
		 		.andExpect(jsonPath("$", Matchers.hasKey("timestamp")))
		 		.andExpect(jsonPath("$", Matchers.hasKey("message")))
		 		.andExpect(jsonPath("$", Matchers.hasKey("details")));
		
		verify(service, VerificationModeFactory.times(1)).findById(id);
		reset(service);
    }

}
