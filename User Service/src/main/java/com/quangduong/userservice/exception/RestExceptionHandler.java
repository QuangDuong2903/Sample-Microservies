package com.quangduong.userservice.exception;

import com.quangduong.exceptionhandler.exception.CommonExceptionHandler;
import com.quangduong.exceptionhandler.response.ProblemDetailsBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class RestExceptionHandler implements CommonExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ProblemDetail usernameAlreadyExistExceptionHandler(UsernameAlreadyExistException e) {
        return ProblemDetailsBuilder.statusAndDetail(HttpStatus.CONFLICT, e.getMessage())
                .type(URI.create("about:blank"))
                .title("Username already exist")
                .build();
    }
}
