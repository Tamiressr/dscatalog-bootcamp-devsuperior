package com.devsuperior.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
  @ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError>objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
	  StandardError error= new StandardError();
	  HttpStatus status= HttpStatus.NOT_FOUND;
	  error.setTimestamp(Instant.now());
	  error.setStatus(status.value());
	  error.setError("Resource not found");
	  error.setMessage(e.getMessage());
	  error.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}
  
  @ExceptionHandler(DatabaseException.class)
 	public ResponseEntity<StandardError>database(DatabaseException e, HttpServletRequest request){
	  HttpStatus status=HttpStatus.BAD_REQUEST;
 	  StandardError error= new StandardError();
 	  error.setTimestamp(Instant.now());
 	  error.setStatus(status.value());
 	  error.setError("Database Exception");
 	  error.setMessage(e.getMessage());
 	  error.setPath(request.getRequestURI());
 		return ResponseEntity.status(status).body(error);
 	}
   
}
