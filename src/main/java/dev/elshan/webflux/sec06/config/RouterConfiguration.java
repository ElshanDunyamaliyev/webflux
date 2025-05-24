package dev.elshan.webflux.sec06.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class RouterConfiguration {

    private final CustomerRequestHandler customerRequestHandler;

    public RouterConfiguration(CustomerRequestHandler customerRequestHandler) {
        this.customerRequestHandler = customerRequestHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> customRoutes(){
        return RouterFunctions.route()
                .GET("/customer",customerRequestHandler::getAllCustomers)
                .GET("/customer/pageable",customerRequestHandler::getAllCustomersPaginated) // Order of routes matter if we write pageable after find by id it guess /id is pageable
                .GET("/customer/{id}", customerRequestHandler::getCustomerById)
                .POST("/customer",customerRequestHandler::saveCustomer)
                .PUT("/customer/{id}",customerRequestHandler::updateCustomer)
                .DELETE("/customer/{id}",customerRequestHandler::deleteCustomer)
                .build();
    }

    // we can also divide routers
//    @Bean
//    public RouterFunction<ServerResponse> customRoutes2(){
//        return RouterFunctions.route()
//                .GET("/customer",customerRequestHandler::getAllCustomers)
//                .GET("/customer/pageable",customerRequestHandler::getAllCustomersPaginated)
//                .GET("/customer/{id}", customerRequestHandler::getCustomerById)
//                .build();
//    }
}
