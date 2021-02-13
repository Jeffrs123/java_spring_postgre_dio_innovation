package com.digital.crud.saladereuniao.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice // Sinalizar q é um advice do controlller q vamos criar
public class GlobalExceptionHandler {

	// ResponseEntity -> Resposta da Entidade
	// Qual a classe que esse global é responsável por lançar a exceção
	@ExceptionHandler (ResourceNotFoundException.class) 
	public ResponseEntity<?> resourceNotFoundException(
			ResourceNotFoundException ex, 
			WebRequest request
			) {
		ErrorDetails errorDetails = new ErrorDetails(
					new Date(), 
					ex.getMessage(), 
					request.getDescription(false)
				);
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandler(
			Exception ex, 
			WebRequest request
			) {
		ErrorDetails errorDetails = new ErrorDetails(
					new Date(), 
					ex.getMessage(), 
					request.getDescription(false)
				);
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

