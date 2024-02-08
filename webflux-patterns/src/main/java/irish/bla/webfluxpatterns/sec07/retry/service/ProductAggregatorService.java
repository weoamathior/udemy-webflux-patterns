package irish.bla.webfluxpatterns.sec07.retry.service;

import irish.bla.webfluxpatterns.sec07.retry.client.ProductClient;
import irish.bla.webfluxpatterns.sec07.retry.client.ReviewClient;
import irish.bla.webfluxpatterns.sec07.retry.dto.Product;
import irish.bla.webfluxpatterns.sec07.retry.dto.ProductAggregate;
import irish.bla.webfluxpatterns.sec07.retry.dto.Review;
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
