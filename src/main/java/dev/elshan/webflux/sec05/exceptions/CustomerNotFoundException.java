package dev.elshan.webflux.sec05.exceptions;

public class CustomerNotFoundException extends RuntimeException{
    private static final String MESSAGE = "Customer not found with given %d id";

    public CustomerNotFoundException(Integer id) {
        super(MESSAGE.formatted(id));
    }
}
