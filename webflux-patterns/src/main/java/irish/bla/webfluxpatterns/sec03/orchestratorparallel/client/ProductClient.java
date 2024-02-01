package irish.bla.webfluxpatterns.sec03.orchestratorparallel.client;

import irish.bla.webfluxpatterns.sec03.orchestratorparallel.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductClient {
    private final WebClient client;
    public ProductClient(@Value("${sec03.product.service}") String baseUrl) {
        client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<ProductResponse> getProduct(Integer id) {
        return client.get().uri("{id}", id)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                // the zip method in the aggregator will coalesce around empty
                .onErrorResume(ex -> Mono.empty());

    }
}
