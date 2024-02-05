package irish.bla.webfluxpatterns.sec04.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class InventoryResponse {
    private Integer productId;
    private Integer quantity;
    private Integer remainingQuantity;
    private Status status;

    public static InventoryResponse buildErrorFrom(InventoryRequest request) {
        return InventoryResponse.create(request.getProductId(), request.getQuantity(), -1, Status.FAILED);
    }
}
