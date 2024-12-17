package com.food.exception;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler({ ResourceNotFoundException.class })
	public ResponseEntity<Object> handleResouceNotFoundException(ResourceNotFoundException exc) {
		ErrorDetails er = new ErrorDetails();
		er.setDate(LocalDate.now());
		er.setMsg(exc.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(error -> error.getDefaultMessage())
				.collect(Collectors.toList());
		Map<String, List<String>> errorResponse = new HashMap<>();
		errorResponse.put("errors", errors);
		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

}
