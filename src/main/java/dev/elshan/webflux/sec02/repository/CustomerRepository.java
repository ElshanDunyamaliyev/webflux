package dev.elshan.webflux.sec02.repository;

import dev.elshan.webflux.sec02.model.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CustomerRepository extends ReactiveCrudRepository <Customer,Integer> {
    Flux<Customer> findByName(String name);
    Flux<Customer> findByEmailEndingWith(String part);
}
