package br.com.magazineluiza.v1.customers.generator;

import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.abstraction.MockUnit;
import net.andreinc.mockneat.types.enums.StringType;
import br.com.magazineluiza.v1.customers.entity.CustomerEntity;

import static net.andreinc.mockneat.types.enums.StringType.NUMBERS;
import static net.andreinc.mockneat.unit.objects.Filler.filler;
import static net.andreinc.mockneat.unit.objects.From.from;
import static net.andreinc.mockneat.unit.text.Strings.strings;
import static net.andreinc.mockneat.unit.types.Ints.ints;
import static net.andreinc.mockneat.unit.types.Longs.longs;
import static net.andreinc.mockneat.unit.user.Names.names;

public class CustomerGenerator {

	private final BranchGenerator branchGenerator;
	private final AddressGenerator addressGenerator;
	
	public CustomerGenerator() {
		this.branchGenerator = new BranchGenerator();
		this.addressGenerator = new AddressGenerator();
	}
	
	public MockUnit<CustomerEntity> schema() {
		Long id = longs().get();
		return filler(CustomerEntity::new)
	    	.setter(CustomerEntity::setName, names().first())
	        .constant(CustomerEntity::setId, id)
	        .setter(CustomerEntity::setNatJur, from(new String[]{"F", "J"}))
	        .setter(CustomerEntity::setDigit, ints().range(0, 10))
	        .setter(CustomerEntity::setCpf, strings().size(11).type(NUMBERS))
	        .setter(CustomerEntity::setCnpj, strings().size(14).type(NUMBERS))
	        .setter(CustomerEntity::setBranch, this.branchGenerator.schema())
	        .setter(CustomerEntity::setAddress, this.addressGenerator.schema(id).list(2));
	}
}
