package br.com.magazineluiza.v1.customers.generator;

import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.abstraction.MockUnit;
import net.andreinc.mockneat.types.enums.StringType;
import br.com.magazineluiza.v1.customers.entity.CustomerEntity;

public class CustomerGenerator {
	private final MockNeat mock = MockNeat.threadLocal();
	private final BranchGenerator branchGenerator;
	private final AddressGenerator addressGenerator;
	
	public CustomerGenerator() {
		this.branchGenerator = new BranchGenerator();
		this.addressGenerator = new AddressGenerator();
	}
	
	public MockUnit<CustomerEntity> schema() {
		Long id = this.mock.longs().get();
		return this.mock.filler(() -> new CustomerEntity())
	    	.setter(CustomerEntity::setName, this.mock.names().first())
	        .setter(CustomerEntity::setId, this.mock.from(new Long[] { id }))
	        .setter(CustomerEntity::setNatJur, this.mock.from(new String[]{"F", "J"}))
	        .setter(CustomerEntity::setDigit, this.mock.from(new Integer[] { 0, 1, 2, 3, 4, 6, 7, 8, 9 }))
	        .setter(CustomerEntity::setCpf, this.mock.strings().size(11).type(StringType.NUMBERS))
	        .setter(CustomerEntity::setCnpj, this.mock.strings().size(14).type(StringType.NUMBERS))
	        .setter(CustomerEntity::setBranch, this.branchGenerator.schema())
	        .setter(CustomerEntity::setAddress, this.addressGenerator.schema(id).list(2));
	}
}
