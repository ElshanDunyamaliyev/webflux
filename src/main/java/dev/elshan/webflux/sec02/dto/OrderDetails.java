package dev.elshan.webflux.sec02.dto;

import java.time.Instant;
import java.util.UUID;

public record OrderDetails(UUID order_id, String customerName, String productName, Integer amount, Instant orderDate) {
}

