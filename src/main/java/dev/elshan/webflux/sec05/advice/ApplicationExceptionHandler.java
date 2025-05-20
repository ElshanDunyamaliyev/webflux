package dev.elshan.webflux.sec05.advice;

import dev.elshan.webflux.sec05.exceptions.CustomerNotFoundException;
import dev.elshan.webflux.sec05.exceptions.InvalidInputException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ProblemDetail handleCustomerNotFoundException(CustomerNotFoundException exception){
        var problemDetail = ProblemDetail.forStatusAndDetail(NOT_FOUND,exception.getMessage());
        problemDetail.setType(URI.create("https://example.com/problems/customer-not-found"));
        problemDetail.setTitle("Customer Not Found");
        return problemDetail;
    }

    @ExceptionHandler(InvalidInputException.class)
    public ProblemDetail handleInvalidInputException(InvalidInputException exception){
        var problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST,exception.getMessage());
        problemDetail.setType(URI.create("https://example.com/problems/bad-request"));
        problemDetail.setTitle("Invalid Input");
        return problemDetail;
    }
}
