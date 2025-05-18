package dev.elshan.webflux.sec02;

import dev.elshan.webflux.sec02.repository.CustomerOrderRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

public class Lec03CustomerOrderTest extends AbstractTest{

    private static final Logger log = LoggerFactory.getLogger(Lec03CustomerOrderTest.class);
    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Test
    public void getCustomerProductsByName(){
        customerOrderRepository.findByCustomerName("mike")
                .doOnNext(product -> log.info("product: {}",product))
                .as(StepVerifier::create)
                .expectNextCount(2L)
                .verifyComplete();
    }

    @Test
    public void getByProductDescription(){
        customerOrderRepository.getByProductDescription("iphone 20")
                .doOnNext(product -> log.info("product: {}",product))
                .as(StepVerifier::create)
                .expectNextCount(2L)
                .verifyComplete();
    }
}
