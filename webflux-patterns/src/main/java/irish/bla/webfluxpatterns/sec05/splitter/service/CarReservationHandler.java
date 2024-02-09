package irish.bla.webfluxpatterns.sec05.splitter.service;

import irish.bla.webfluxpatterns.sec05.splitter.client.CarClient;
import irish.bla.webfluxpatterns.sec05.splitter.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class CarReservationHandler extends ReservationHandler {

    private final CarClient client;

    @Override
    protected ReservationType getType() {
        return ReservationType.CAR;
    }

    @Override
    protected Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> req) {
        return req.map(this::toCarRequest)
                .transform(this.client::reserve)
                .map(this::toResponse)
                .doOnError(ex -> System.out.println(ex));
    }

    private CarReservationRequest toCarRequest(ReservationItemRequest request) {
        return CarReservationRequest.create(request.getCity(), request.getFrom(), request.getTo(), request.getCategory());
    }

    private ReservationItemResponse toResponse(CarReservationResponse carResponse) {
        return ReservationItemResponse.create(carResponse.getReservationId(),
                this.getType(),
                carResponse.getCategory(),
                carResponse.getCity(),
                carResponse.getPickup(),
                carResponse.getDrop(),
                carResponse.getPrice());
    }
}
