package irish.bla.webfluxpatterns.sec03.orchestratorparallel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class PaymentResponse {
    private Integer userId;
    private String name;
    private Integer balance;
    private Status status;

    public static PaymentResponse buildErrorFrom(PaymentRequest request) {
        return PaymentResponse.create(request.getUserId(), null, request.getAmount(), Status.FAILED);
    }
}
