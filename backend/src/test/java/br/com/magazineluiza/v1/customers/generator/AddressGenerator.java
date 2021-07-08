package br.com.magazineluiza.v1.customers.generator;

import br.com.magazineluiza.v1.customers.entity.AddressEntity;
import br.com.magazineluiza.v1.customers.entity.AddressEntityPK;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.abstraction.MockConstValue;
import net.andreinc.mockneat.abstraction.MockUnit;

import static net.andreinc.mockneat.unit.address.Cities.cities;
import static net.andreinc.mockneat.unit.objects.Filler.filler;
import static net.andreinc.mockneat.unit.objects.From.from;
import static net.andreinc.mockneat.unit.text.Strings.strings;
import static net.andreinc.mockneat.unit.types.Ints.ints;

public class AddressGenerator {
	
	public MockUnit<AddressEntity> schema(Long customerId) {
		Integer id = ints().get();

		return filler(AddressEntity::new)
				.setter(AddressEntity::setCity, cities().us())
				.setter(AddressEntity::setPk, filler(AddressEntityPK::new)
												.constant(AddressEntityPK::setId, id)
												.constant(AddressEntityPK::setCustomerId, customerId))
				.constant(AddressEntity::setId, id)
				.setter(AddressEntity::setComplement, strings())
				.setter(AddressEntity::setDistrict, strings())
				.setter(AddressEntity::setNumber, ints())
				.setter(AddressEntity::setState, from(new String[] { "PR", "SP", "RS" }))
				.setter(AddressEntity::setStreetType, from(new String[] { "R", "AV" }))
				.setter(AddressEntity::setStreet, strings())
				.setter(AddressEntity::setZipCode, strings().size(8));
	}
}
