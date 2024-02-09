package irish.bla.webfluxpatterns.sec03.orchestratorparallel.util;

import irish.bla.webfluxpatterns.sec03.orchestratorparallel.dto.InventoryRequest;
import irish.bla.webfluxpatterns.sec03.orchestratorparallel.dto.OrchestrationRequestContext;
import irish.bla.webfluxpatterns.sec03.orchestratorparallel.dto.PaymentRequest;
import irish.bla.webfluxpatterns.sec03.orchestratorparallel.dto.ShippingRequest;

public class OrchestrationUtil {
    public static void buildRequestContext(OrchestrationRequestContext context) {

        buildPaymentRequest(context);
        buildInventoryRequest(context);
        buildShippingRequest(context);

    }

    private static void buildPaymentRequest(OrchestrationRequestContext ctx) {
        PaymentRequest request = PaymentRequest.create(
                ctx.getOrderRequest().getUserId(),
                ctx.getProductPrice() * ctx.getOrderRequest().getQuantity(),
                ctx.getOrderId()
        );
        ctx.setPaymentRequest(request);
    }

    private static void buildInventoryRequest(OrchestrationRequestContext ctx) {
        var inventoryRequest = InventoryRequest.create(
                ctx.getOrderId(),
                ctx.getOrderRequest().getProductId(),
                ctx.getOrderRequest().getQuantity()
        );
        ctx.setInventoryRequest(inventoryRequest);
    }

    private static void buildShippingRequest(OrchestrationRequestContext ctx) {
        var shippingRequest = ShippingRequest.create(
                ctx.getOrderRequest().getQuantity(),
                ctx.getOrderRequest().getUserId(),
                ctx.getOrderId()
        );
        ctx.setShippingRequest(shippingRequest);
    }
}
