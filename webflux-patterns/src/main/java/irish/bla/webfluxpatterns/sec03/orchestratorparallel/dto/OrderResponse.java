package irish.bla.webfluxpatterns.sec03.orchestratorparallel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class OrderResponse {
    private Integer userId;
    private Integer productId;
    private UUID orderId;
    private Status status;
    private Address shippingAddress;
    private String expectedDelivery;
}
