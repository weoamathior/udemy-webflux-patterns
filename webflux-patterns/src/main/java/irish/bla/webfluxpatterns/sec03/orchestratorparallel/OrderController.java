package irish.bla.webfluxpatterns.sec03.orchestratorparallel;

import irish.bla.webfluxpatterns.sec03.orchestratorparallel.dto.OrderRequest;
import irish.bla.webfluxpatterns.sec03.orchestratorparallel.dto.OrderResponse;
import irish.bla.webfluxpatterns.sec03.orchestratorparallel.service.OrchestratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("sec03")
public class OrderController {
    private final OrchestratorService service;

    @PostMapping("order")
    public Mono<ResponseEntity<OrderResponse>> placeOrder(@RequestBody Mono<OrderRequest> mono) {
        return this.service.placeOrder(mono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
