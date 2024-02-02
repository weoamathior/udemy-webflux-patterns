package irish.bla.webfluxpatterns.sec03.orchestratorparallel.service;

import irish.bla.webfluxpatterns.sec03.orchestratorparallel.client.UserClient;
import irish.bla.webfluxpatterns.sec03.orchestratorparallel.dto.OrchestrationRequestContext;
import irish.bla.webfluxpatterns.sec03.orchestratorparallel.dto.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;
@Service
@RequiredArgsConstructor
public class PaymentOrchestrator extends Orchestrator{
    private final UserClient userClient;
    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext ctx) {
        return this.userClient.deduct(ctx.getPaymentRequest())
                .doOnNext(ctx::setPaymentResponse)
                .thenReturn(ctx);
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return ctx -> Status.SUCCESS.equals(ctx.getPaymentResponse().getStatus());
    }

    @Override
    public Consumer<OrchestrationRequestContext> cancel() {
        return ctx -> Mono.just(ctx)
                .filter(isSuccess())
                .map(OrchestrationRequestContext::getPaymentRequest)
                .flatMap(this.userClient::refund)
                .subscribe();
    }
}
