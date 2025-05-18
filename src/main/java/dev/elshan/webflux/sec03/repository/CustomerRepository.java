package dev.elshan.webflux.sec03.repository;

import dev.elshan.webflux.sec03.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveCrudRepository<Customer,Integer> {
    Flux<Customer> findByName(String name);
    Flux<Customer> findByEmailEndingWith(String part);

    Flux<Customer> findAllBy(Pageable pageable);

    @Modifying
    @Query("delete from customer where id = :id")
    Mono<Boolean> deleteCustomer(Integer id);
}
