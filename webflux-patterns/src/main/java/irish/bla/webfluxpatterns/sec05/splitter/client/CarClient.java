package irish.bla.webfluxpatterns.sec05.splitter.client;

import irish.bla.webfluxpatterns.sec05.splitter.dto.CarReservationRequest;
import irish.bla.webfluxpatterns.sec05.splitter.dto.CarReservationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CarClient {
    private final WebClient client;


    public CarClient(@Value("${sec05.car.service}") String url) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Flux<CarReservationResponse> reserve(Flux<CarReservationRequest> req) {
        return this.client
                .post()
                .body(req, CarReservationRequest.class)
                .retrieve()
                .bodyToFlux(CarReservationResponse.class)
                .onErrorResume(ex -> Mono.empty());

    }


}
