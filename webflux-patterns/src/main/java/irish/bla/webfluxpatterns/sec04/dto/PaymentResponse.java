package irish.bla.webfluxpatterns.sec04.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class PaymentResponse {
    private UUID paymentId;
    private Integer userId;
    private String name;
    private Integer balance;
    private Status status;

    public static PaymentResponse buildErrorFrom(PaymentRequest request) {
        return PaymentResponse.create(null, request.getUserId(), null, request.getAmount(), Status.FAILED);
    }
}
