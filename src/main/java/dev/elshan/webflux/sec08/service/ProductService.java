package dev.elshan.webflux.sec08.service;

import dev.elshan.webflux.sec08.dto.ProductDto;
import dev.elshan.webflux.sec08.mapper.ProductMapper;
import dev.elshan.webflux.sec08.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Flux<ProductDto> save(Flux<ProductDto> flux) {
        return flux.map(ProductMapper::toEntity)
                .as(repository::saveAll).map(ProductMapper::toDto);
    }

    public Flux<ProductDto> downloadProducts() {
        return repository.findAll().map(ProductMapper::toDto);
    }

    public Mono<Long> getCount() {
        return repository.findAll().count();
    }
}
