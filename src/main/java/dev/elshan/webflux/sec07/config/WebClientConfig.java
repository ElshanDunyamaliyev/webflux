package dev.elshan.webflux.sec07.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient orderClient(){
      return WebClient.builder()
//              .baseUrl("http://order-service.com")
              .baseUrl("http://localhost:7070/demo02")
              .build();
    };

    @Bean
    public WebClient paymentClient(){
        return WebClient.builder()
//                .baseUrl("http://payment-service.com")
                .baseUrl("http://localhost:7070/demo02")
                .build();
    };
}
