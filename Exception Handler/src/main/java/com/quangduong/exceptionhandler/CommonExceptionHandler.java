package com.quangduong.exceptionhandler;

import com.quangduong.exceptionhandler.exception.ResourceNotFoundException;
import com.quangduong.exceptionhandler.response.ProblemDetailsBuilder;
import com.quangduong.exceptionhandler.response.ViolationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.URI;
import java.util.*;

public interface CommonExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    default ProblemDetail resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
        return ProblemDetailsBuilder.statusAndDetail(HttpStatus.NOT_FOUND, e.getMessage())
                .type(URI.create("about:blank"))
                .title("Resource not found")
                .build();
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    default ProblemDetail bindExceptionHandler(BindException e) {
        Map<String, ViolationError> violations = new HashMap<>();

        for (var error : e.getBindingResult().getFieldErrors()) {
            if (violations.containsKey(error.getField())) {
                violations.get(error.getField()).messages().add(error.getDefaultMessage());
            } else {
                violations.put(
                        error.getField(),
                        new ViolationError(error.getField(), List.of(Optional.ofNullable(error.getDefaultMessage())
                                .orElse("This field is not valid")))
                );
            }
        }

        return ProblemDetailsBuilder.statusAndDetail(HttpStatus.BAD_REQUEST, "Validation error on request object")
                .type(URI.create("about:blank"))
                .title("Validation error")
                .violations(new ArrayList<>(violations.values()))
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    default ProblemDetail handleGlobalException(Exception e) {
        return ProblemDetailsBuilder.statusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())
                .type(URI.create("about:blank"))
                .title("Internal server error")
                .build();
    }
}
