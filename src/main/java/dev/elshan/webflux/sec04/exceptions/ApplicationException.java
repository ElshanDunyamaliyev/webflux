package dev.elshan.webflux.sec04.exceptions;

import reactor.core.publisher.Mono;

public class ApplicationException {

    public static <T> Mono<T> customerNotFound(Integer id){
        return Mono.error(new CustomerNotFoundException(id));
    }

    public static <T> Mono<T> missingName(){
        return Mono.error(new InvalidInputException("Name is missing"));
    }

    public static <T> Mono<T> missingEmail(){
        return Mono.error(new InvalidInputException("Email is missing"));
    }
}
