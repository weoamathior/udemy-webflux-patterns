package irish.bla.webfluxpatterns.sec05.splitter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class RoomReservationResponse {
    private UUID reservationId;
    private String city;
    private LocalDate checkIn;
    private LocalDate checkout;
    private String category;
    private Integer price;
}
