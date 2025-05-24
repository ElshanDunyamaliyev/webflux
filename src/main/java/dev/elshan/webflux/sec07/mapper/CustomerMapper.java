package dev.elshan.webflux.sec07.mapper;

import dev.elshan.webflux.sec07.dto.CustomerDto;
import dev.elshan.webflux.sec07.entity.Customer;

public class CustomerMapper {

    public static Customer toEntity(CustomerDto dto){
        var customer = new Customer();
        customer.setEmail(dto.email());
        customer.setName(dto.name());
//        customer.setId(dto.id());
        return customer;
    }

    public static CustomerDto toDto(Customer customer){
        return new CustomerDto(customer.getId(),customer.getName(),customer.getEmail());
    }
}
