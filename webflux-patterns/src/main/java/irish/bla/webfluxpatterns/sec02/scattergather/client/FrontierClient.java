package irish.bla.webfluxpatterns.sec02.scattergather.client;

import irish.bla.webfluxpatterns.sec02.scattergather.dto.FlightResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FrontierClient {
    private final WebClient client;
    public FrontierClient(@Value("${sec02.frontier.service}") String baseUrl) {
        client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<FlightResult> getFlights(String from, String to) {
        return this.client
                .post()
                .bodyValue(FrontierRequest.create(from,to))
                .retrieve()
                .bodyToFlux(FlightResult.class)
                .onErrorResume(ex -> Mono.empty());
    }

    @Data
    @AllArgsConstructor(staticName = "create")
    @NoArgsConstructor
    private static class FrontierRequest {
        private String from;
        private String to;

    }

}
