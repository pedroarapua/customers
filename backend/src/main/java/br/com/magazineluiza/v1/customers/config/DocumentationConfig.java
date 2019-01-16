package br.com.magazineluiza.v1.customers.config;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "documentation.enabled", havingValue = "true")
public class DocumentationConfig {
	@Value("${spring.application.name}")
	private String applicationName;
	@Value("${spring.application.group}")
	private String applicationGroup;

    @Bean
    @ConditionalOnProperty(name = "documentation.swagger.enabled", havingValue = "true")
    public Docket swaggerDocumentation() {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage(applicationGroup + "." + applicationName + ".controller"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())
            .securitySchemes(Arrays.asList(apiKey()))
            .securityContexts(Collections.singletonList(securityContext()))
            .consumes(new HashSet<String>(Arrays.asList("application/json")))
			.produces(new HashSet<String>(Arrays.asList("application/json")))
            .useDefaultResponseMessages(false)
            .globalResponseMessage(RequestMethod.GET, newArrayList(new ResponseMessageBuilder().code(500)
                .message("500 message")
                .responseModel(new ModelRef("Error"))
                .build(),
                new ResponseMessageBuilder().code(403)
                    .message("Forbidden!!!!!")
                    .build()));
    }

    private ApiInfo apiInfo() {
    	ApiInfo apiInfo = new ApiInfo(applicationName + " API", applicationName + " operation API.", "API", "Terms of service", new Contact("John Doe", "www.example.com", "myeaddress@company.com"), "License of API", "API license URL", Collections.emptyList());
        return apiInfo;
    }
    
    private ApiKey apiKey() {
    	return new ApiKey("Bearer", "Authorization", "header");
    }
    
    private SecurityContext securityContext() {
    	return SecurityContext.builder()
	        .securityReferences(defaultAuth())
	        .forPaths(PathSelectors.any())
	        .build();
    }

    private List<SecurityReference> defaultAuth() {
	    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	    authorizationScopes[0] = authorizationScope;
	    return Arrays.asList(new SecurityReference("Bearer", authorizationScopes));
    }
}
