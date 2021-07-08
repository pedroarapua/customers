package br.com.magazineluiza.v1.customers.generator;

import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.abstraction.MockUnit;
import br.com.magazineluiza.v1.customers.entity.SigninEntity;

import static net.andreinc.mockneat.unit.objects.Filler.filler;
import static net.andreinc.mockneat.unit.text.Strings.strings;

public class SigninGenerator {
	
	public MockUnit<SigninEntity> schema() {
		return filler(SigninEntity::new)
				.setter(SigninEntity::setToken, strings().size(10));
	}
}
