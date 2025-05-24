package dev.elshan.webflux.sec09.service;

import dev.elshan.webflux.sec09.dto.ProductDto;
import dev.elshan.webflux.sec09.mapper.ProductMapper;
import dev.elshan.webflux.sec09.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.Random;
import java.util.random.RandomGenerator;

@Service
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository repository;
    private final Sinks.Many<ProductDto> sinks;

    public ProductService(ProductRepository repository, Sinks.Many<ProductDto> sinks) {
        this.repository = repository;
        this.sinks = sinks;
    }

    public Mono<ProductDto> save(Mono<ProductDto> mono) {
        return mono.map(ProductMapper::toEntity)
                .flatMap(repository::save)
                .map(ProductMapper::toDto)
                .doOnNext(sinks::tryEmitNext);
    }

    @PostConstruct
    public void filterPrice(){
        Flux.range(1,1000)
                .delayElements(Duration.ofSeconds(1))
                .map(i -> new ProductDto(null,"product "+ i, Random.from(RandomGenerator.getDefault()).nextInt(20,100)))
                .flatMap(productDto -> save(Mono.just(productDto)))
                .doOnNext(productDto -> log.info("{}",productDto))
                .doOnNext(sinks::tryEmitNext)
                .subscribe();
    }

    public Flux<ProductDto> productStream(){
        return sinks.asFlux();
    }


}
