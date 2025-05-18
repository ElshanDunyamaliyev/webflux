package dev.elshan.webflux.sec02;

import dev.elshan.webflux.sec02.model.Product;
import dev.elshan.webflux.sec02.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Lec02Test extends AbstractTest{
    private static final Logger log = LoggerFactory.getLogger(Lec02Test.class);

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void findProductsBetweenRange(){
        productRepository.findProductsByPriceBetween(400,2000)
                .doOnNext(product -> log.info("product: {}",product))
                .as(StepVerifier::create)
                .assertNext(product -> Assertions.assertTrue(product.getPrice() > 400 && product.getPrice() < 2000))
                .expectNextCount(5L)
                .expectComplete()
                .verify();
    }

    @Test
    public void pageable(){
        productRepository.findAllBy(PageRequest.of(0,3).withSort(Sort.by("price").ascending()))
                .doOnNext(product -> log.info("product: {}",product))
                .as(StepVerifier::create)
                .assertNext(product -> Assertions.assertEquals(200,product.getPrice()))
                .expectNextCount(2L)
                .expectComplete()
                .verify();
    }
}
