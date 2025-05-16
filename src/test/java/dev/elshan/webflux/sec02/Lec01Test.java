package dev.elshan.webflux.sec02;

import dev.elshan.webflux.sec02.model.Customer;
import dev.elshan.webflux.sec02.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

public class Lec01Test extends AbstractTest{

    private static final Logger log = LoggerFactory.getLogger(Lec01Test.class);

    @Autowired
    private  CustomerRepository customerRepository;

    @Test
    public void findAll(){
        this.customerRepository.findAll().doOnNext(customer -> log.info("customer : {}",customer))
                .as(StepVerifier::create).expectNextCount(10).expectComplete().verify();
    }

    @Test
    public void findById(){
        this.customerRepository.findById(2).doOnNext(customer -> log.info("customer : {}",customer))
                .as(StepVerifier::create).assertNext(customer -> Assertions.assertEquals("mike",customer.getName())).expectComplete().verify();
    }

    @Test
    public void findByName(){
        this.customerRepository.findByName("jake").doOnNext(customer -> log.info("customer : {}",customer))
                .as(StepVerifier::create).assertNext(customer -> Assertions.assertEquals("jake@gmail.com",customer.getEmail())).expectComplete().verify();
    }

    @Test
    public void findByEmailEndingWith(){
        this.customerRepository
                .findByEmailEndingWith("ke@gmail.com")
                .doOnNext(customer -> log.info("customer : {}",customer))
                .as(StepVerifier::create)
                .assertNext(customer -> Assertions.assertTrue(customer.getEmail().equals("jake@gmail.com") || customer.getEmail().equals("mike@gmail.com")))
                .expectNextCount(1).expectComplete().verify();
    }

    @Test
    public void save(){
        var customer = new Customer();
        customer.setName("Eleven");
        customer.setEmail("Eleven@gmail.com");
        this.customerRepository.save(customer)
                .doOnNext(c -> log.info("saved: {}",c))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertNotNull(c.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    public void getCount(){
        var customer = new Customer();
        customer.setName("Eleven");
        customer.setEmail("Eleven@gmail.com");
        this.customerRepository.save(customer)
                .doOnNext(c -> log.info("saved: {}",c))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertNotNull(c.getId()))
                .expectComplete()
                .verify();
        this.customerRepository.count()
                .as(StepVerifier::create)
                .expectNext(11L)
                .expectComplete()
                .verify();
    }

    @Test
    public void delete(){
        this.customerRepository.deleteById(10)
                .then(this.customerRepository.count())
                .as(StepVerifier::create)
                .expectNext(9L)
                .expectComplete()
                .verify();
    }
}
