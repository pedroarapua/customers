package br.com.magazineluiza.v1.customers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magazineluiza.v1.customers.entity.SigninEntity;
import br.com.magazineluiza.v1.customers.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/auth")
@Api(value="Auth Management System")
public class AuthController {
	
	@Autowired
    AuthService authService;
	
	@ApiOperation(value = "signin to request token", response = Object.class)
    @ApiResponses(value = {
    	    @ApiResponse(code = 200, message = "Successfully retrieved token")
    	})
    @PostMapping(value = "/signin")
    public ResponseEntity<SigninEntity> signin() {
		SigninEntity signin = authService.signin();
    	
    	return ResponseEntity.status(HttpStatus.OK).body(signin);
    }
}
