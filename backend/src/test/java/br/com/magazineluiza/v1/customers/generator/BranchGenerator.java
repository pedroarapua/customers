package br.com.magazineluiza.v1.customers.generator;

import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.abstraction.MockUnit;
import br.com.magazineluiza.v1.customers.entity.BranchEntity;

public class BranchGenerator {
	private final MockNeat mock = MockNeat.threadLocal();
	
	public MockUnit<BranchEntity> schema() {
		return this.mock.filler(() -> new BranchEntity())
			.setter(BranchEntity::setId, this.mock.ints());
	}
}
