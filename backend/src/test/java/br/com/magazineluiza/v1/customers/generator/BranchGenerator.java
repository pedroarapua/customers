package br.com.magazineluiza.v1.customers.generator;

import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.abstraction.MockUnit;
import br.com.magazineluiza.v1.customers.entity.BranchEntity;

import static net.andreinc.mockneat.unit.objects.Filler.filler;
import static net.andreinc.mockneat.unit.types.Ints.ints;

public class BranchGenerator {
	
	public MockUnit<BranchEntity> schema() {
		return filler(BranchEntity::new)
				.setter(BranchEntity::setId, ints());
	}
}
