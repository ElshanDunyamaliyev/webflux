package dev.elshan.webflux.sec03;

import dev.elshan.webflux.sec02.model.Customer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest
public class WebClientTest1 {

    private static final Logger log = LoggerFactory.getLogger(WebClientTest1.class);
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getAllCustomers() {
        webTestClient.get().uri("/customer")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Customer.class)
                .hasSize(10)
                .value(customers -> log.info("{}", customers));
    }

    @Test
    public void getAllCustomersPaginated() {
        webTestClient.get().uri("/customer/pageable?pageNumber=3&pageSize=2")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .consumeWith(customers -> log.info("{}", new String(customers.getResponseBody())))
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].id").isEqualTo(5)
                .jsonPath("$[1].id").isEqualTo(6)
        ;
    }

    @Test
    public void getCustomersById() {
        webTestClient.get().uri("/customer/1")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .consumeWith(customers -> log.info("{}", new String(customers.getResponseBody())))
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("sam")
        ;
    }

    @Test
    public void saveCustomer() {
        var customer = new Customer();
        customer.setEmail("qa@gmail.com");
        customer.setName("qaEngineer");
        webTestClient.post().uri("/customer")
                .bodyValue(customer)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(Customer.class)
                .consumeWith(customerFluxExchangeResult -> log.info("is created : {}",customerFluxExchangeResult));

        webTestClient.delete().uri("/customer/11")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody().isEmpty();
    }


}
