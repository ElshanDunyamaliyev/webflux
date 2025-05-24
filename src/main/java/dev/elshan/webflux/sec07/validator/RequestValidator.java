package dev.elshan.webflux.sec07.validator;

import dev.elshan.webflux.sec07.dto.CustomerDto;
import dev.elshan.webflux.sec07.exceptions.ApplicationException;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class RequestValidator {

    public static UnaryOperator<Mono<CustomerDto>> validate(){
        return customerDtoMono -> customerDtoMono.filter(hasName())
                .switchIfEmpty(ApplicationException.missingName())
                .filter(hasEmail())
                .switchIfEmpty(ApplicationException.missingEmail());
    }

    private static Predicate<CustomerDto> hasName(){
        return customerDto -> Objects.nonNull(customerDto.name());
    }

    private static Predicate<CustomerDto> hasEmail(){
        return customerDto -> Objects.nonNull(customerDto.email()) && customerDto.email().contains("@");
    }
}
