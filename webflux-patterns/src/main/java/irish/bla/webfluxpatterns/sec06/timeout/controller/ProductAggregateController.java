package irish.bla.webfluxpatterns.sec06.timeout.controller;

import irish.bla.webfluxpatterns.sec06.timeout.dto.ProductAggregate;
import irish.bla.webfluxpatterns.sec06.timeout.service.ProductAggregatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("sec06")
@RequiredArgsConstructor
public class ProductAggregateController {
    private final ProductAggregatorService productAggregatorService;

    @GetMapping("product/{id}")
    public Mono<ResponseEntity<ProductAggregate>> getProductAggregate(@PathVariable Integer id) {
        return productAggregatorService.aggregate(id).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
