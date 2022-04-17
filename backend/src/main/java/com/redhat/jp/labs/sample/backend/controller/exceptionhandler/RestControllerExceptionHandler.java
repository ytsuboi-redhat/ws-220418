package com.redhat.jp.labs.sample.backend.controller.exceptionhandler;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandler.class);

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.warn("input arguments not valid!", e);
		return handleExceptionInternal(e, new ErrorResponse("E001", "input arguments not valid!"), null,
				HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Object> handleValidationFailure(ValidationException e, WebRequest request) {
		logger.warn("input arguments not valid!", e);
		return handleExceptionInternal(e, new ErrorResponse("E001", "input arguments not valid!"), null,
				HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleInternalServerError(Exception e, WebRequest request) {
		logger.error("Exception occured!!", e);
		return handleExceptionInternal(e, new ErrorResponse("E999", "Exception occured!!"), null,
				HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

}
