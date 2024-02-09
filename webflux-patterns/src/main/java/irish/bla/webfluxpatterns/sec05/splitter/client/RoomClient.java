package irish.bla.webfluxpatterns.sec05.splitter.client;

import irish.bla.webfluxpatterns.sec05.splitter.dto.RoomReservationRequest;
import irish.bla.webfluxpatterns.sec05.splitter.dto.RoomReservationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RoomClient {
    private final WebClient client;


    public RoomClient(@Value("${sec05.room.service}") String url) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Flux<RoomReservationResponse> reserve(Flux<RoomReservationRequest> req) {
        return this.client
                .post()
                .body(req, RoomReservationRequest.class)
                .retrieve()
                .bodyToFlux(RoomReservationResponse.class)
                .onErrorResume(ex -> Mono.empty());

    }

}
