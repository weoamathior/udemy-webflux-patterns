package irish.bla.webfluxpatterns.sec05.splitter.controller;

import irish.bla.webfluxpatterns.sec05.splitter.dto.ReservationItemRequest;
import irish.bla.webfluxpatterns.sec05.splitter.dto.ReservationResponse;
import irish.bla.webfluxpatterns.sec05.splitter.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("sec05")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService service;

    @PostMapping("reserve")
    public Mono<ReservationResponse> reserve(@RequestBody Flux<ReservationItemRequest> flux) {
        return this.service.reserve(flux);
    }
}
