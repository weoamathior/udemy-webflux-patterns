package irish.bla.webfluxpatterns.sec04.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ShippingResponse {
    private UUID orderId;
    private Integer quantity;
    private Status status;
    private  String expectedDelivery;
    private Address address;

    public static ShippingResponse buildErrorFrom(ShippingRequest request) {
        return ShippingResponse.create(request.getOrderId(), request.getQuantity(), Status.FAILED, null, null);
    }
}
