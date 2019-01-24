package br.com.magazineluiza.v1.customers.repository;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.magazineluiza.v1.customers.builder.CustomerBuilder;
import br.com.magazineluiza.v1.customers.entity.AddressEntity;
import br.com.magazineluiza.v1.customers.entity.CustomerEntity;
import br.com.magazineluiza.v1.customers.repository.specification.GenericSpecificationsBuilder;
import br.com.magazineluiza.v1.customers.util.repository.SearchOperation;
import br.com.magazineluiza.v1.customers.util.repository.SpecSearchCriteria;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class CustomerRespositoryTest {
	@Autowired
	private TestEntityManager em;
	@Autowired
    private CustomerRepository repository;
	private final CustomerBuilder customerBuilder;
    private static final int offset = 0;
	private static final int limit = 1;
	private static final Long id = Long.valueOf(1);
    
    public CustomerRespositoryTest() {
		this.customerBuilder = new CustomerBuilder();
	}
    
    @Test
    public void whenFilterTest() throws Exception {
    	List<CustomerEntity> customers = customerBuilder.filter(offset, limit, null, null, id);
		CustomerEntity expected = customers.get(0);
		expected.setAddress(new ArrayList<AddressEntity>());
		this.em.persist(expected);
		
		Pageable pageable = PageRequest.of(offset, limit, Direction.ASC, "name", "id");
		Page<CustomerEntity> expectedPage = new PageImpl<CustomerEntity>(customers, pageable, limit);
		GenericSpecificationsBuilder<CustomerEntity> builder = new GenericSpecificationsBuilder<CustomerEntity>();
		
		builder.with(new SpecSearchCriteria("id", SearchOperation.EQUALITY, id));
		
		Page<CustomerEntity> actual = this.repository.findAll(
			builder.build(), 
			pageable
		);
		
		assertEquals(expectedPage, actual); 
    }
    
    @Test
    public void whenFilterNotFoundTest() throws Exception {
    	Pageable pageable = PageRequest.of(offset, limit, Direction.ASC, "name", "id");
		Page<CustomerEntity> expectedPage = new PageImpl<CustomerEntity>(new ArrayList<CustomerEntity>(), pageable, 0);
GenericSpecificationsBuilder<CustomerEntity> builder = new GenericSpecificationsBuilder<CustomerEntity>();
		
		builder.with(new SpecSearchCriteria("id", SearchOperation.EQUALITY, -1));
		
		Page<CustomerEntity> actual = this.repository.findAll(
			builder.build(), 
			pageable
		);
		
		assertEquals(expectedPage, actual); 
    }
    
    @Test
    public void whenFindByIdTest() throws Exception {
    	Optional<CustomerEntity> expected = customerBuilder.findById();
    	CustomerEntity customer = expected.get();
    	customer.setAddress(new ArrayList<AddressEntity>());
		this.em.persist(customer);
		
		Optional<CustomerEntity> actual = this.repository.findById(customer.getId());
		
		assertEquals(expected, actual); 
    }
    
    @Test
    public void whenFindByIdNotFoundTest() throws Exception {
    	Optional<CustomerEntity> expected = Optional.empty();
    	Optional<CustomerEntity> actual = this.repository.findById(Long.valueOf(-1));
		
		assertEquals(expected, actual); 
    }
}
