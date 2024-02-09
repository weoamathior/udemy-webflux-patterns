package irish.bla.webfluxpatterns.sec02.scattergather.service;

import irish.bla.webfluxpatterns.sec02.scattergather.client.DeltaClient;
import irish.bla.webfluxpatterns.sec02.scattergather.client.FrontierClient;
import irish.bla.webfluxpatterns.sec02.scattergather.client.JetBlueClient;
import irish.bla.webfluxpatterns.sec02.scattergather.dto.FlightResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class FlightSearchService {
    private final DeltaClient deltaClient;
    private final FrontierClient frontierClient;
    private final JetBlueClient jetBlueClient;

    public Flux<FlightResult> getFlights(String from, String to) {
        return Flux.merge(
                deltaClient.getFlights(from,to),
                frontierClient.getFlights(from,to),
                jetBlueClient.getFlights(from,to)
        ).take(Duration.ofSeconds(3));
    }

}
