package dev.elshan.webflux.sec06.config;


import dev.elshan.webflux.sec06.dto.CustomerDto;
import dev.elshan.webflux.sec06.exceptions.ApplicationException;
import dev.elshan.webflux.sec06.service.CustomerService;
import dev.elshan.webflux.sec06.validator.RequestValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class CustomerRequestHandler {
    private final CustomerService customerService;

    public CustomerRequestHandler(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Mono<ServerResponse> getAllCustomers(ServerRequest request){
       return customerService.findAll().as(customerDtoFlux -> ServerResponse.ok().body(customerDtoFlux, CustomerDto.class));
    }

    public Mono<ServerResponse> getAllCustomersPaginated(ServerRequest request){
        int pageNumber = request.queryParam("pageNumber")
                .map(Integer::parseInt)
//                .map(pageNumber1 -> pageNumber1 - 1)
                .orElse(0);
        int pageSize = request.queryParam("pageSize")
                .map(Integer::parseInt)
                .orElse(10);
        return customerService.findAll(pageNumber,pageSize).as(customerDtoFlux -> ServerResponse.ok().body(customerDtoFlux, CustomerDto.class));
    }

    public Mono<ServerResponse> getCustomerById(ServerRequest request){
        var id = Integer.parseInt(request.pathVariable("id"));
        return customerService.findCustomerById(id)
                .switchIfEmpty(ApplicationException.customerNotFound(id))
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request){
        return request.bodyToMono(CustomerDto.class)
                .transform(RequestValidator.validate())
                .as(customerService::save)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> updateCustomer(ServerRequest request){
        var id = Integer.parseInt(request.pathVariable("id"));
        return request.bodyToMono(CustomerDto.class)
                .transform(RequestValidator.validate())
                .as(validatedDto -> customerService.update(id,validatedDto))
                .switchIfEmpty(ApplicationException.customerNotFound(id))
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> deleteCustomer(ServerRequest request){
        var id = Integer.parseInt(request.pathVariable("id"));
        return customerService.delete(id)
                .filter(b -> b)
                .switchIfEmpty(ApplicationException.customerNotFound(id))
                .flatMap(ServerResponse.ok()::bodyValue);
    }
}
