package irish.bla.webfluxpatterns.sec05.splitter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class RoomReservationRequest {
    private String city;
    private LocalDate checkIn;
    private LocalDate checkout;
    private String category;
}
