package dev.elshan.webflux.sec03.controller;

import dev.elshan.webflux.sec03.dto.CustomerDto;
import dev.elshan.webflux.sec03.service.CustomerService;
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
    public Mono<ResponseEntity<CustomerDto>> findById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @PostMapping
    public Mono<CustomerDto> save(@RequestBody Mono<CustomerDto> dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CustomerDto>> update(@PathVariable Integer id, @RequestBody Mono<CustomerDto> dto) {
        return service.update(id, dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Integer id) {
        return service.delete(id)
                .filter(aBoolean -> aBoolean)
                .map(b -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
