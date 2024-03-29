package irish.bla.webfluxpatterns.sec04.service;

import irish.bla.webfluxpatterns.sec04.dto.*;
import irish.bla.webfluxpatterns.sec04.util.DebugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrchestratorService {
    private final OrderFulfillmentService orderFulfillmentService;
    private final OrderCancellationService orderCancellationService;

    public Mono<OrderResponse> placeOrder(Mono<OrderRequest> mono) {
        return mono.map(OrchestrationRequestContext::new)
                .flatMap(orderFulfillmentService::placeOrder)
                .doOnNext(this::doOrderPostProcessing)
                .doOnNext(DebugUtil::print)
                .map(this::toOrderResponseFrom);

    }

    private OrderResponse toOrderResponseFrom(OrchestrationRequestContext ctx) {
        boolean isSuccess = Status.SUCCESS.equals(ctx.getStatus());
        Address address = isSuccess ? ctx.getShippingResponse().getAddress() : null;
        String expectedDeliveryDate = isSuccess ? ctx.getShippingResponse().getExpectedDelivery() : null;

        return OrderResponse.create(
                ctx.getOrderRequest().getUserId(),
                ctx.getOrderRequest().getProductId(),
                ctx.getOrderId(),
                ctx.getStatus(),
                address,
                expectedDeliveryDate);
    }

    private void doOrderPostProcessing(OrchestrationRequestContext ctx) {
        if (Status.FAILED.equals(ctx.getStatus())) {
            this.orderCancellationService.cancelOrder(ctx);
        }
    }

}
