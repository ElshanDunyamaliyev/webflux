package dev.elshan.webflux.sec08;

import dev.elshan.webflux.sec08.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class ProductsUploadDownloadTest {

    private static final Logger log = LoggerFactory.getLogger(ProductsUploadDownloadTest.class);
    private final ProductClient productClient = new ProductClient();

    @Test
    public void upload() {
//        var products = Flux.just(new ProductDto(null, "test", 200)).delayElements(Duration.ofSeconds(3));
        var products = Flux.range(1,1_000_00)
                .map(i -> new ProductDto(null, "test " + i, 200));

        productClient.uploadProducts(products)
//                .doOnNext(pr -> log.info("{}", pr))
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

        log.info("Upload Finished, download starting");

        productClient.downloadProducts()
                .doOnNext(pr -> log.info("{}", pr))
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    public void download() {
        productClient.downloadProducts()
                .doOnNext(pr -> log.info("{}", pr))
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}
