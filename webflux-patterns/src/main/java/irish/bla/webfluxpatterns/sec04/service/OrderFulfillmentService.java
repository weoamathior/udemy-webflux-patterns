package irish.bla.webfluxpatterns.sec04.service;

import irish.bla.webfluxpatterns.sec04.client.ProductClient;
import irish.bla.webfluxpatterns.sec04.dto.OrchestrationRequestContext;
import irish.bla.webfluxpatterns.sec04.dto.ProductResponse;
import irish.bla.webfluxpatterns.sec04.dto.Status;
import irish.bla.webfluxpatterns.sec04.util.OrchestrationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderFulfillmentService {
    private final ProductClient productClient;
    private final PaymentOrchestrator paymentOrchestrator;
    private final InventoryOrchestrator inventoryOrchestrator;
    private final ShippingOrchestrator shippingOrchestrator;


    public Mono<OrchestrationRequestContext> placeOrder(OrchestrationRequestContext ctx) {
        return this.getProduct(ctx)
                .doOnNext(OrchestrationUtil::buildPaymentRequest)
                .flatMap(this.paymentOrchestrator::create)
                .doOnNext(OrchestrationUtil::buildInventoryRequest)
                .flatMap(this.inventoryOrchestrator::create)
                .doOnNext(OrchestrationUtil::buildShippingRequest)
                .flatMap(this.shippingOrchestrator::create)
                .doOnNext(c -> c.setStatus(Status.SUCCESS))
                .doOnError(ex -> ctx.setStatus(Status.FAILED))
                .onErrorReturn(ctx);
    }
    private Mono<OrchestrationRequestContext> getProduct(OrchestrationRequestContext ctx) {
        return this.productClient.getProduct(ctx.getOrderRequest().getProductId())
                .map(ProductResponse::getPrice)
                .doOnNext(ctx::setProductPrice)
                .map(i -> ctx);
    }

}
