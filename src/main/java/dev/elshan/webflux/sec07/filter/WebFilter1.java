package dev.elshan.webflux.sec07.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

//@Component
//public class WebFilter1 implements WebFilter {
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        HttpHeaders headers = exchange.getRequest().getHeaders();
//        String authToken = headers.getFirst("auth-token");
//        String method = exchange.getRequest().getMethod().name();
//
//        if ("secret123".equals(authToken)) {
//            if (!"GET".equalsIgnoreCase(method)) {
//                // Reject non-GET requests
//                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
//                return exchange.getResponse().setComplete();
//            }
//        } else if (!"secret456".equals(authToken)) {
//            // Token is not valid
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        // All checks passed — continue the filter chain
//        return chain.filter(exchange);
//    }
//}
