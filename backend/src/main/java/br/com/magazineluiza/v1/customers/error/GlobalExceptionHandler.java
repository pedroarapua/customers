package br.com.magazineluiza.v1.customers.error;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.magazineluiza.v1.customers.error.entity.ErrorDetailsEntity;
import br.com.magazineluiza.v1.customers.exception.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		return this.response(ex, request, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> accessDeniedException(AccessDeniedException ex, WebRequest request) {
		return this.response(ex, request, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
		return this.response(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
    @ResponseBody
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return this.response(ex, request, HttpStatus.NOT_FOUND);
    }
	
	private ResponseEntity<Object> response(Exception ex, WebRequest request, HttpStatus status) {
		HttpServletRequest requestAux = ((ServletWebRequest)request).getRequest();
		ErrorDetailsEntity errorDetails = new ErrorDetailsEntity(new Date(), ex.getMessage(), requestAux.getServletPath(), status.getReasonPhrase(), status.value());
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
