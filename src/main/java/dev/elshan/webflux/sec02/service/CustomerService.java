package dev.elshan.webflux.sec02.service;

import dev.elshan.webflux.sec02.model.Customer;
import dev.elshan.webflux.sec02.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Mono<Customer> findById(Integer id){
        return repository.findById(id);
    };

    public Flux<Customer> findAll(){
        return repository.findAll();
    }
}
