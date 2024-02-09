package irish.bla.webfluxpatterns.sec03.orchestratorparallel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class InventoryRequest {
    private UUID orderId;
    private Integer productId;
    private Integer quantity;
}
