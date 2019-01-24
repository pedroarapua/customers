package br.com.magazineluiza.v1.customers.generator;

import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.abstraction.MockUnit;

public class BaseGenerator<T> {
	protected static final MockNeat mock = MockNeat.threadLocal();
	private MockUnit<T> mockSchema;
	public BaseGenerator(MockUnit<T> mockSchema) {
		this.setMockSchema(mockSchema);
	}
	
	public MockUnit<T> getMockSchema() {
		return mockSchema;
	}

	public void setMockSchema(MockUnit<T> mockSchema) {
		this.mockSchema = mockSchema;
	}
	
	public T generate() {
		return this.getMockSchema().get();
	}
}
