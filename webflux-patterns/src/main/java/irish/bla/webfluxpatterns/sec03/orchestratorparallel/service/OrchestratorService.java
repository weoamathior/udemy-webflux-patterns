package irish.bla.webfluxpatterns.sec03.orchestratorparallel.service;

import irish.bla.webfluxpatterns.sec03.orchestratorparallel.client.ProductClient;
import irish.bla.webfluxpatterns.sec03.orchestratorparallel.dto.*;
import irish.bla.webfluxpatterns.sec03.orchestratorparallel.util.DebugUtil;
import irish.bla.webfluxpatterns.sec03.orchestratorparallel.util.OrchestrationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrchestratorService {
    private final ProductClient client;
    private final OrderFulfillmentService orderFulfillmentService;
    private final OrderCancellationService orderCancellationService;

    public Mono<OrderResponse> placeOrder(Mono<OrderRequest> mono) {
        return mono.map(OrchestrationRequestContext::new)
                .flatMap(this::getProduct)
                .doOnNext(OrchestrationUtil::buildRequestContext)
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

    private Mono<OrchestrationRequestContext> getProduct(OrchestrationRequestContext ctx) {
        return this.client.getProduct(ctx.getOrderRequest().getProductId())
                .map(ProductResponse::getPrice)
                .doOnNext(ctx::setProductPrice)
                .thenReturn(ctx);
    }
}
