package dev.elshan.webflux.sec09.mapper;

import dev.elshan.webflux.sec09.dto.ProductDto;
import dev.elshan.webflux.sec09.model.Product;

public class ProductMapper {
    public static Product toEntity(ProductDto dto) {
        var product = new Product();
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        return product;
    }

    public static ProductDto toDto(Product entity) {
        return new ProductDto(entity.getId(), entity.getDescription(), entity.getPrice());
    }
}
