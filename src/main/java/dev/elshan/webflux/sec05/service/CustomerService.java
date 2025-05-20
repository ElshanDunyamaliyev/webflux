package dev.elshan.webflux.sec05.service;


import dev.elshan.webflux.sec05.dto.CustomerDto;
import dev.elshan.webflux.sec05.entity.Customer;
import dev.elshan.webflux.sec05.mapper.CustomerMapper;
import dev.elshan.webflux.sec05.repository.CustomerRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Flux<CustomerDto> findAll() {
        return repository.findAll().map(CustomerMapper::toDto);
    }

    public Flux<CustomerDto> findAll(int pageNumber, int pageSize) {
        return repository.findAllBy(PageRequest.of(pageNumber - 1,pageSize)).map(CustomerMapper::toDto);
    }

    public Mono<CustomerDto> findById(Integer id) {
        return repository.findById(id).map(CustomerMapper::toDto);
    }

    public Mono<Customer> findCustomerById(Integer id) {
        return repository.findById(id);
    }

    public Mono<CustomerDto> save(Mono<CustomerDto> dto) {
        return dto.map(CustomerMapper::toEntity)
                .flatMap(repository::save)
                .map(CustomerMapper::toDto);
    }

    public Mono<CustomerDto> update(Integer id, Mono<CustomerDto> dto) {
        return findCustomerById(id)
                .zipWith(dto)
                .flatMap(tuple -> {
                    var oldEntity = tuple.getT1();
                    var updateRequest = tuple.getT2();
                    oldEntity.setEmail(updateRequest.email());
                    oldEntity.setName(updateRequest.name());
                    return repository.save(oldEntity);
                })
                .map(CustomerMapper::toDto);
    }

    public Mono<Boolean> delete(Integer id) {
        return repository.deleteCustomer(id);
    }
}
