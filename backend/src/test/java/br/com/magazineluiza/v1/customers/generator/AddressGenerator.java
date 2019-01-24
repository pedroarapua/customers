package br.com.magazineluiza.v1.customers.generator;

import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.abstraction.MockUnit;
import net.andreinc.mockneat.unit.types.Ints;
import br.com.magazineluiza.v1.customers.entity.AddressEntity;
import br.com.magazineluiza.v1.customers.entity.AddressEntityPK;

public class AddressGenerator {
	private final MockNeat mock = MockNeat.threadLocal();
	
	public MockUnit<AddressEntity> schema(Long customerId) {
		Integer id = this.mock.ints().get();
		return this.mock.filler(() -> new AddressEntity())
	    	.setter(AddressEntity::setCity, this.mock.strings())
	    	.setter(AddressEntity::setPk, this.mock.filler(() -> new AddressEntityPK())
    			.setter(AddressEntityPK::setId, this.mock.from(new Integer[] { id }))
    			.setter(AddressEntityPK::setCustomerId, this.mock.from(new Long[] { customerId })))
	        .setter(AddressEntity::setId, this.mock.from(new Integer[] { id }))
	        .setter(AddressEntity::setComplement, this.mock.strings())
	        .setter(AddressEntity::setDistrict, this.mock.strings())
	        .setter(AddressEntity::setNumber, this.mock.ints())
	        .setter(AddressEntity::setState, this.mock.from(new String[] { "PR", "SP", "RS" }))
	        .setter(AddressEntity::setStreetType, this.mock.from(new String[] { "R", "AV" }))
	        .setter(AddressEntity::setStreet, this.mock.strings())
	        .setter(AddressEntity::setZipCode, this.mock.strings().size(8));
	}
}
