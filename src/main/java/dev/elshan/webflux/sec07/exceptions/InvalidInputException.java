package dev.elshan.webflux.sec07.exceptions;

public class InvalidInputException extends RuntimeException{

    public InvalidInputException(String message) {
        super(message);
    }
}
