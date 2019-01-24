package br.com.magazineluiza.v1.customers.actuator;

public class Actuator{
	
	private App app;

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}
	
	public class App {
		
		private String name;
		private String version;
		private String encoding;
		private Java java;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		public String getEncoding() {
			return encoding;
		}
		public void setEncoding(String encoding) {
			this.encoding = encoding;
		}
		public Java getJava() {
			return java;
		}
		public void setJava(Java java) {
			this.java = java;
		}
		
		public class Java {
			
			private String version;
			
			public String getVersion() {
				return version;
			}
			public void setVersion(String version) {
				this.version = version;
			}
		}
	}	
}
