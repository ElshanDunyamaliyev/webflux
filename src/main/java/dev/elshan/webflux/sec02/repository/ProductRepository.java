package dev.elshan.webflux.sec02.repository;

import dev.elshan.webflux.sec02.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductRepository extends ReactiveCrudRepository<Product,Integer> {
}
