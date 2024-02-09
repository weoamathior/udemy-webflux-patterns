package irish.bla.webfluxpatterns.sec04.service;

import irish.bla.webfluxpatterns.sec04.client.InventoryClient;
import irish.bla.webfluxpatterns.sec04.dto.OrchestrationRequestContext;
import irish.bla.webfluxpatterns.sec04.dto.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class InventoryOrchestrator extends Orchestrator {
    private final InventoryClient client;
    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext ctx) {
        return this.client
                .deduct(ctx.getInventoryRequest())
                .doOnNext(ctx::setInventoryResponse)
                .thenReturn(ctx)
                .handle(this.statusHandler());
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return ctx -> Objects.nonNull(ctx.getInventoryResponse()) && Status.SUCCESS.equals(ctx.getInventoryResponse().getStatus());
    }

    @Override
    public Consumer<OrchestrationRequestContext> cancel() {
        return ctx -> Mono.just(ctx)
                .filter(isSuccess())
                .map(OrchestrationRequestContext::getInventoryRequest)
                .flatMap(this.client::restore)
                .subscribe();
    }
}
