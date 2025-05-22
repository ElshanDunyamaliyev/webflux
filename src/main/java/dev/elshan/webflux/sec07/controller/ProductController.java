package dev.elshan.webflux.sec07.controller;

import dev.elshan.webflux.sec01.Product;
import dev.elshan.webflux.sec07.dto.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/product")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final WebClient orderClient;

    public ProductController(WebClient orderClient) {
        this.orderClient = orderClient;
    }

    @GetMapping(value = "/{id}")
    public Mono<Product> getProductById(@PathVariable Integer id) {
        return orderClient.get()
                .uri("/lec01/product/" + id)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(product -> log.info("{}", product));
    }

    // Sending 100 request
    @GetMapping(value = "/get-100-products")
    public void get100Products() throws InterruptedException {
        for (int i = 1; i < 100; i++) {
            orderClient.get()
                    .uri("/lec01/product/{id}", i)
                    .retrieve()
                    .bodyToMono(Product.class)
                    .doOnNext(product -> log.info("{}", product))
                    .subscribe();
        }
        Thread.sleep(Duration.ofSeconds(2));
    }

    @GetMapping(value = "/header/{id}")
    public Mono<ProductDto> addHeader(@PathVariable Integer id){
        return orderClient.get()
                .uri("/lec04/product/{id}", id)
                .headers(httpHeaders -> {
                    httpHeaders.add("caller-id","order-service");
                })
                .retrieve()
                .bodyToMono(ProductDto.class)
                .doOnNext(product -> log.info("{}", product));
    }

    @GetMapping(produces = "text/event-stream")
    public Flux<Product> getAllProducts() {
        return orderClient.get().uri("/lec02/product/stream").retrieve().bodyToFlux(Product.class);
    }

    @PostMapping
    public Mono<Product> addProduct(@RequestBody ProductDto productDto) {
        return orderClient.post().uri("/lec03/product").bodyValue(productDto)
                .retrieve().bodyToMono(Product.class);
    }
}
