package dev.elshan.webflux.sec02.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("product")
public class Product {
    @Id
    Integer id;
    String description;
    Integer price;
}
