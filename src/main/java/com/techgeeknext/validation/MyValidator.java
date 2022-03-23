package com.techgeeknext.validation;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@ControllerAdvice
public class MyValidator extends ResponseEntityExceptionHandler {
	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String,Object> errors1=new HashMap<>();
        Map<String,String> err=new HashMap<>();
       ex.getBindingResult().getAllErrors().forEach((error)->{
            String field= ((FieldError) error).getField();
            String txt= ((FieldError) error).getDefaultMessage();
            err.put(field,txt);

        });

        errors1.put("status",422);
        errors1.put("errors",err);
        return new ResponseEntity<>(errors1,HttpStatus.OK);

    }
}