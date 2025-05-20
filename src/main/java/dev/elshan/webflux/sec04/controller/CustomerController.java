package dev.elshan.webflux.sec04.controller;

import dev.elshan.webflux.sec04.dto.CustomerDto;
import dev.elshan.webflux.sec04.exceptions.ApplicationException;
import dev.elshan.webflux.sec04.service.CustomerService;
import dev.elshan.webflux.sec04.validator.RequestValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<CustomerDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/pageable")
    public Flux<CustomerDto> findAll(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return service.findAll(pageNumber,pageSize);
    }

    @GetMapping("/{id}")
    public Mono<CustomerDto> findById(@PathVariable Integer id) {
        return service.findById(id)
                .switchIfEmpty(ApplicationException.customerNotFound(id));
    }


    @PostMapping
    public Mono<CustomerDto> save(@RequestBody Mono<CustomerDto> mono) {
        return mono.transform(RequestValidator.validate())
                .as(service::save);

    }

    @PutMapping("/{id}")
    public Mono<CustomerDto> update(@PathVariable Integer id, @RequestBody Mono<CustomerDto> mono) {
        return mono.transform(RequestValidator.validate())
                .as(validReq -> service.update(id,validReq))
                .switchIfEmpty(ApplicationException.customerNotFound(id));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Integer id) {
        return service.delete(id)
                .filter(aBoolean -> aBoolean)
                .switchIfEmpty(ApplicationException.customerNotFound(id))
                .then();
    }
}
