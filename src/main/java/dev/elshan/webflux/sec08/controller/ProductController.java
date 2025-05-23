package dev.elshan.webflux.sec08.controller;

import dev.elshan.webflux.sec08.dto.ProductDto;
import dev.elshan.webflux.sec08.dto.UploadResponse;
import dev.elshan.webflux.sec08.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<UploadResponse> addProduct(@RequestBody Flux<ProductDto> productDtoFlux){
        log.info("invoked");
       return productService.save(productDtoFlux.doOnNext(dto -> log.info("received {}",dto)))
               .then(productService.getCount())
               .map(count -> new UploadResponse(UUID.randomUUID(),count));
    }

    @GetMapping(value = "/download", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDto> downloadProduct(){
        return productService.downloadProducts();
    }
}
