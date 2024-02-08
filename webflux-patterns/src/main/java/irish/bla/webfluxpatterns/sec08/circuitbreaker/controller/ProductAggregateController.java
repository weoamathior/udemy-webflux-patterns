package irish.bla.webfluxpatterns.sec08.circuitbreaker.controller;

import irish.bla.webfluxpatterns.sec08.circuitbreaker.dto.ProductAggregate;
import irish.bla.webfluxpatterns.sec08.circuitbreaker.service.ProductAggregatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("sec08")
@RequiredArgsConstructor
public class ProductAggregateController {
    private final ProductAggregatorService productAggregatorService;

    @GetMapping("product/{id}")
    public Mono<ResponseEntity<ProductAggregate>> getProductAggregate(@PathVariable Integer id) {
        return productAggregatorService.aggregate(id).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
