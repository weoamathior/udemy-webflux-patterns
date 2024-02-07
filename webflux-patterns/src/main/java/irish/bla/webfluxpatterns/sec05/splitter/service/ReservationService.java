package irish.bla.webfluxpatterns.sec05.splitter.service;

import irish.bla.webfluxpatterns.sec05.splitter.dto.ReservationItemRequest;
import irish.bla.webfluxpatterns.sec05.splitter.dto.ReservationItemResponse;
import irish.bla.webfluxpatterns.sec05.splitter.dto.ReservationResponse;
import irish.bla.webfluxpatterns.sec05.splitter.dto.ReservationType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final Map<ReservationType,ReservationHandler> map;
    public ReservationService(List<ReservationHandler> handlers) {
        this.map = handlers.stream().collect(Collectors.toMap(ReservationHandler::getType, Function.identity()));
    }

    public Mono<ReservationResponse> reserve(Flux<ReservationItemRequest> flux) {
        return flux.groupBy(ReservationItemRequest::getType) // splitter - one flux into N fluxes
                .flatMap(this::aggregator)
                .collectList()
                .map(this::toResponse);
    }

    private ReservationResponse toResponse(List<ReservationItemResponse> list) {
        return ReservationResponse.create(
                UUID.randomUUID(),
                list.stream().mapToInt(ReservationItemResponse::getPrice).sum(),
                list
        );
    }

    private Flux<ReservationItemResponse> aggregator(GroupedFlux<ReservationType,ReservationItemRequest> groupedFlux) {
        ReservationType key = groupedFlux.key();
        ReservationHandler handler = map.get(key);
        return handler.reserve(groupedFlux);
    }
}
