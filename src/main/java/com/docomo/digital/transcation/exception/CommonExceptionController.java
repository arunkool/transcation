package com.docomo.digital.transcation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionController {
	   @ExceptionHandler(value = ResourceNotFoundException.class)
	   public ResponseEntity<Object> notFoundException(ResourceNotFoundException exception) {
	      return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.NOT_FOUND);
	   }
	   @ExceptionHandler(value = NotEnoughBalanceException.class)
	   public ResponseEntity<Object> lowBalance(NotEnoughBalanceException exception) {
	      return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
	   }
	}
