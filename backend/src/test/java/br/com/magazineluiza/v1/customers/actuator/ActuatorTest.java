package br.com.magazineluiza.v1.customers.actuator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ActuatorTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Value("${info.app.name}")
	private String name;
	@Value("${info.app.description}")
	private String description;
	@Value("${info.app.version}")
	private String version;
	@Value("${info.app.encoding}")
	private String encoding;
	@Value("${info.app.java.version}")
	private String javaVersion;
	private final String basePath = "/actuator";
	
	@Test
	public void healthInsecureByDefault() {
		ResponseEntity<String> entity = this.restTemplate.getForEntity(basePath + "/health",
				String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("\"status\":\"UP\"");
		assertThat(entity.getBody()).doesNotContain("\"hello\":\"1\"");
	}

	@Test
	public void infoInsecureByDefault() {
		ResponseEntity<Actuator> entity = this.restTemplate.getForEntity(basePath + "/info",
				Actuator.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		Actuator actuator = entity.getBody();
		assertThat(actuator.getApp().getName()).contains(name);
		assertThat(actuator.getApp().getVersion()).contains(version);
		assertThat(actuator.getApp().getEncoding()).contains(encoding);
		assertThat(actuator.getApp().getJava().getVersion()).contains(javaVersion);
	}
}
