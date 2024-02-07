package irish.bla.webfluxpatterns.sec05.splitter.service;

import irish.bla.webfluxpatterns.sec05.splitter.client.RoomClient;
import irish.bla.webfluxpatterns.sec05.splitter.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class RoomReservationHandler extends ReservationHandler{
    private final RoomClient client;

    @Override
    protected ReservationType getType() {
        return ReservationType.ROOM;
    }

    @Override
    protected Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> req) {
        return req.map(this::toRoomRequest)
                .transform(this.client::reserve)
                .map(this::toResponse);
    }

    private RoomReservationRequest toRoomRequest(ReservationItemRequest request) {
        return RoomReservationRequest.create(request.getCity(), request.getFrom(), request.getTo(), request.getCategory());
    }

    private ReservationItemResponse toResponse(RoomReservationResponse roomResponse) {
        return ReservationItemResponse.create(roomResponse.getReservationId(),
                this.getType(),
                roomResponse.getCategory(),
                roomResponse.getCity(),
                roomResponse.getCheckIn(),
                roomResponse.getCheckout(),
                roomResponse.getPrice());
    }
}
