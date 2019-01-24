package br.com.magazineluiza.v1.customers.generator;

import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.abstraction.MockUnit;
import br.com.magazineluiza.v1.customers.entity.SigninEntity;

public class SigninGenerator {
	private final MockNeat mock = MockNeat.threadLocal();
	
	public MockUnit<SigninEntity> schema() {
		return this.mock.filler(() -> new SigninEntity())
			.setter(SigninEntity::setToken, this.mock.strings().size(10));
	}
}
