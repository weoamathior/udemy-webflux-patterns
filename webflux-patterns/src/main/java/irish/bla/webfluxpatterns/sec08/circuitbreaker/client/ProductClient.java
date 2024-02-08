package irish.bla.webfluxpatterns.sec08.circuitbreaker.client;

import irish.bla.webfluxpatterns.sec08.circuitbreaker.dto.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductClient {
    private final WebClient client;
    public ProductClient(@Value("${sec08.product.service}") String baseUrl) {
        client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<Product> getProduct(Integer id) {
        return client.get().uri("{id}", id)
                .retrieve()
                .bodyToMono(Product.class)
                // the zip method in the aggregator will coalesce around empty
                .onErrorResume(ex -> Mono.empty());

    }
}
