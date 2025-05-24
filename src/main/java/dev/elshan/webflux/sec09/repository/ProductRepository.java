package dev.elshan.webflux.sec09.repository;

import dev.elshan.webflux.sec09.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<Product,Integer> {
    Flux<Product> findProductsByPriceBetween(Integer priceAfter, Integer priceBefore);
    Flux<Product> findAllBy(Pageable pageable);
}
