package irish.bla.webfluxpatterns.sec10.bulkhead.service;

import irish.bla.webfluxpatterns.sec10.bulkhead.client.ProductClient;
import irish.bla.webfluxpatterns.sec10.bulkhead.client.ReviewClient;
import irish.bla.webfluxpatterns.sec10.bulkhead.dto.Product;
import irish.bla.webfluxpatterns.sec10.bulkhead.dto.ProductAggregate;
import irish.bla.webfluxpatterns.sec10.bulkhead.dto.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAggregatorService {
    private final ProductClient productClient;
    private final ReviewClient reviewClient;

    public Mono<ProductAggregate> aggregate(Integer id) {
        return Mono.zip(
                        productClient.getProduct(id),
                        reviewClient.getReview(id))
                .map(t -> toDto(t.getT1(), t.getT2()));

    }

    private ProductAggregate toDto(Product product, List<Review> reviews) {

        return ProductAggregate.builder()
                .id(product.getId())
                .category(product.getCategory())
                .description(product.getDescription())
                .reviews(reviews)
                .build();
    }
}
