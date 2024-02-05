package irish.bla.webfluxpatterns.sec04.client;

import irish.bla.webfluxpatterns.sec04.dto.ShippingRequest;
import irish.bla.webfluxpatterns.sec04.dto.ShippingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ShippingClient {
    private static final String SCHEDULE = "schedule";
    private static final String CANCEL = "cancel";
    private final WebClient client;

    public ShippingClient(@Value("${sec04.shipping.service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<ShippingResponse> schedule(ShippingRequest request) {
        return callService(SCHEDULE, request);
    }

    public Mono<ShippingResponse> cancel(ShippingRequest request) {
        return callService(CANCEL, request);
    }

    private Mono<ShippingResponse> callService(String endpoint, ShippingRequest request) {
        return this.client.post()
                .uri(endpoint)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ShippingResponse.class)
                .onErrorReturn(ShippingResponse.buildErrorFrom(request));
    }
}
