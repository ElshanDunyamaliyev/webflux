package dev.elshan.webflux.sec02.repository;

import dev.elshan.webflux.sec02.model.CustomerOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface CustomerOrderRepository extends ReactiveCrudRepository<CustomerOrder, UUID> {
}
