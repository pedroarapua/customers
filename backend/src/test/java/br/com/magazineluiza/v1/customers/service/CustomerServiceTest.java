package br.com.magazineluiza.v1.customers.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.magazineluiza.v1.customers.builder.CustomerBuilder;
import br.com.magazineluiza.v1.customers.entity.CustomerEntity;
import br.com.magazineluiza.v1.customers.repository.CustomerRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration
public class CustomerServiceTest {
	@Configuration
	static class CustomerServiceTestConfiguration {
		@Bean
		public CustomerService service() {
			return new CustomerService();
		}
		
		@Bean
		public CustomerRepository repository() {
			return Mockito.mock(CustomerRepository.class);
		}
		
		@Bean
		public CustomerBuilder builder() {
			return new CustomerBuilder();
		}
	}
	
	@Autowired
	private CustomerService service;
	@Autowired
	private CustomerRepository repository;
	@Autowired
	private CustomerBuilder builder;
	private static final int offset = 0;
	private static final int limit = 1;
	private static final String cpf = "11111111111";
	private static final String cnpj = "11111111111111";
	private static final Long id = Long.valueOf(1);
	
	@Test
    public void whenFilterByIdTest() throws Exception {
		List<CustomerEntity> expected = this.builder.filter(offset, limit, null, null, id);
		Page<CustomerEntity> expectedPage = new PageImpl<CustomerEntity>(expected);
		Pageable pageable = PageRequest.of(offset, limit, Direction.ASC, "name", "id");
		
		given(this.repository.findAll(
			any(Specification.class),
			eq(pageable)
		)).willReturn(expectedPage);
		
		List<CustomerEntity> actual = this.service.filter(id, null, null, offset, limit);
		
		verify(this.repository, VerificationModeFactory.times(1))
			.findAll(any(Specification.class), eq(pageable));
		assertEquals(expected, actual);
    }
	
	@Test
    public void whenFilterByCpfTest() throws Exception {
		List<CustomerEntity> expected = this.builder.filter(offset, limit, cpf, null, null);
		Page<CustomerEntity> expectedPage = new PageImpl<CustomerEntity>(expected);
		Pageable pageable = PageRequest.of(offset, limit, Direction.ASC, "name", "id");
		
		given(repository.findAll(
			any(Specification.class),
			eq(pageable)
		)).willReturn(expectedPage);
		
		List<CustomerEntity> actual = this.service.filter(null, cpf, null, offset, limit);
		
		verify(this.repository, VerificationModeFactory.times(1))
			.findAll(any(Specification.class), eq(pageable));
		assertEquals(expected, actual); 
    }
	
	@Test
    public void whenFilterByCnpjTest() throws Exception {
		List<CustomerEntity> expected = this.builder.filter(offset, limit, null, cnpj, null);
		Page<CustomerEntity> expectedPage = new PageImpl<CustomerEntity>(expected);
		Pageable pageable = PageRequest.of(offset, limit, Direction.ASC, "name", "id");
		
		given(repository.findAll(
			any(Specification.class),
			eq(pageable)
		)).willReturn(expectedPage);
		
		List<CustomerEntity> actual = this.service.filter(null, null, cnpj, offset, limit);
		
		verify(this.repository, VerificationModeFactory.times(1))
			.findAll(any(Specification.class), eq(pageable));
		assertEquals(expected, actual); 
    }
	
	@Test
    public void whenFilterReturnEmptyListTest() throws Exception {
		List<CustomerEntity> expected = new ArrayList<CustomerEntity>();
		Page<CustomerEntity> expectedPage = new PageImpl<CustomerEntity>(expected);
		Pageable pageable = PageRequest.of(offset, limit, Direction.ASC, "name", "id");
		
		given(repository.findAll(
			any(Specification.class),
			eq(pageable)
		)).willReturn(expectedPage);
		
		List<CustomerEntity> actual = this.service.filter(null, null, cnpj, offset, limit);
		
		verify(this.repository, VerificationModeFactory.times(1))
			.findAll(any(Specification.class), eq(pageable));
		assertEquals(expected, actual); 
    }
	
	@Test
    public void whenFindByIdTest() throws Exception {
		Optional<CustomerEntity> expected = builder.findById();
		CustomerEntity customer = expected.get();
		
		given(this.repository.findById(customer.getId())).willReturn(expected);
		
		Optional<CustomerEntity> actual = this.service.findById(customer.getId());
		
		verify(this.repository, VerificationModeFactory.times(1)).findById(customer.getId());
		assertEquals(expected, actual); 
    }
	
	@Test
    public void whenFindByIdReturnEmptyObjectTest() throws Exception {
		Optional<CustomerEntity> expected = Optional.empty();
		
		given(repository.findById(id)).willReturn(expected);
		
		Optional<CustomerEntity> actual = this.service.findById(id);
		
		verify(this.repository, VerificationModeFactory.times(1)).findById(id);
		assertEquals(expected, actual); 
    }
}
