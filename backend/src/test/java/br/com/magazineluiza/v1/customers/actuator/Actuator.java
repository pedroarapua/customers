package br.com.magazineluiza.v1.customers.actuator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Actuator{
	
	private @NonNull App app;

	@Getter @Setter @NoArgsConstructor
	public class App {
		
		private @NonNull String name;
		private @NonNull String version;
		private @NonNull String encoding;
		private @NonNull Java java;
		
		@Getter @Setter @NoArgsConstructor
		public class Java {
			
			private @NonNull String version;

		}
	}	
}
