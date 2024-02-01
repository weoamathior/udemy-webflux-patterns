package irish.bla.webfluxpatterns.sec03.orchestratorparallel.client;

import irish.bla.webfluxpatterns.sec03.orchestratorparallel.dto.InventoryRequest;
import irish.bla.webfluxpatterns.sec03.orchestratorparallel.dto.InventoryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class InventoryClient {
    private static final String DEDUCT = "deduct";
    private static final String RESTORE = "restore";
    private final WebClient client;

    public InventoryClient(@Value("${sec03.inventory.service") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<InventoryResponse> deduct(InventoryRequest request) {
        return callService(DEDUCT, request);
    }

    public Mono<InventoryResponse> restore(InventoryRequest request) {
        return callService(RESTORE, request);
    }

    private Mono<InventoryResponse> callService(String endpoint, InventoryRequest request) {
        return this.client.post()
                .uri(endpoint)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(InventoryResponse.class)
                .onErrorReturn(InventoryResponse.buildErrorFrom(request));
    }
}
