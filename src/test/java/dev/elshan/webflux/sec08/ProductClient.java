package dev.elshan.webflux.sec08;

import dev.elshan.webflux.sec08.dto.ProductDto;
import dev.elshan.webflux.sec08.dto.UploadResponse;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ProductClient {

    private final WebClient client = WebClient.builder()
            .baseUrl("http://localhost:8080").build();

    public Mono<UploadResponse> uploadProducts(Flux<ProductDto> productDtoFlux){
        return this.client.post().uri("/product/upload")
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(productDtoFlux, ProductDto.class)
                .retrieve()
                .bodyToMono(UploadResponse.class);
    }

    public Flux<ProductDto> downloadProducts(){
        return this.client.get().uri("/product/download")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(ProductDto.class);
    }
}
