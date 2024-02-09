package irish.bla.webfluxpatterns.sec05.splitter.service;

import irish.bla.webfluxpatterns.sec05.splitter.dto.ReservationItemRequest;
import irish.bla.webfluxpatterns.sec05.splitter.dto.ReservationItemResponse;
import irish.bla.webfluxpatterns.sec05.splitter.dto.ReservationType;
import reactor.core.publisher.Flux;

public abstract class ReservationHandler {
    protected abstract ReservationType getType();
    protected abstract Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> req);
}
