package irish.bla.webfluxpatterns.sec03.orchestratorparallel.service;

import irish.bla.webfluxpatterns.sec03.orchestratorparallel.dto.OrchestrationRequestContext;
import irish.bla.webfluxpatterns.sec03.orchestratorparallel.dto.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderFulfillmentService {
    private final List<Orchestrator> orchestrators;

    public Mono<OrchestrationRequestContext> placeOrder(OrchestrationRequestContext ctx) {
        List<Mono<OrchestrationRequestContext>> listOfPublishers = orchestrators.stream().map(o -> o.create(ctx)).collect(Collectors.toList());

        // The array here contains three OrchestrationRequestContext references, all pointing to the same context.
        // That's why we can just pull the first one
        // Also, multiple threads are acting on the same OrchestrationRequestContext, but this is "okay" because
        // each knows its own business, updating its own domain object
        return Mono.zip(listOfPublishers, a -> a[0])
                .cast(OrchestrationRequestContext.class)
                .doOnNext(this::updateStatus);
    }

    private void updateStatus(OrchestrationRequestContext ctx) {
        boolean allSuccess = this.orchestrators.stream().allMatch(o -> o.isSuccess().test(ctx));
        Status status = allSuccess ? Status.SUCCESS : Status.FAILED;
        ctx.setStatus(status);
    }
}
