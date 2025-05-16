package dev.elshan.webflux.sec02.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.util.UUID;

@Table("customer_order")
public class CustomerOrder {
    @Id
    UUID orderId;
    Integer customerId;
    Integer productId;
    Integer amount;
    Timestamp orderDate;
}
