package dev.elshan.webflux.sec09.controller;

import dev.elshan.webflux.sec09.dto.ProductDto;
import dev.elshan.webflux.sec09.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Mono<ProductDto> addProduct(@RequestBody Mono<ProductDto> productDtoMono) {
        return productService.save(productDtoMono);
    }

    // service to service communication da APPLICATION_NDJSON_VALUE
    // browser de TEXT_EVENT_STREAM_VALUE
    @GetMapping(value = "/stream/{price}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDto> addProduct(@PathVariable Integer price) {
        return productService.productStream().filter(productDto -> productDto.price() <= price);
    }
}
