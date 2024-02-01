package irish.bla.webfluxpatterns.sec03.orchestratorparallel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ShippingRequest {
    private Integer quantity;
    private Integer userid;
    private UUID orderId;
}
