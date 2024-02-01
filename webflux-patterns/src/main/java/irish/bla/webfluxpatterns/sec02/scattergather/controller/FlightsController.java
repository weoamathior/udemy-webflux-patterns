package irish.bla.webfluxpatterns.sec02.scattergather.controller;

import irish.bla.webfluxpatterns.sec02.scattergather.dto.FlightResult;
import irish.bla.webfluxpatterns.sec02.scattergather.service.FlightSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("sec02")
@RequiredArgsConstructor
public class FlightsController {
    private final FlightSearchService flightSearchService;

    @GetMapping(value = "flights/{from}/{to}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<FlightResult> getFlights(@PathVariable String from, @PathVariable String to) {
        return flightSearchService.getFlights(from,to);
    }

}
