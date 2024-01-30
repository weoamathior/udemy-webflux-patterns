package irish.bla.webfluxpatterns.sec01.service;

import irish.bla.webfluxpatterns.sec01.client.ProductClient;
import irish.bla.webfluxpatterns.sec01.client.PromotionClient;
import irish.bla.webfluxpatterns.sec01.client.ReviewClient;
import irish.bla.webfluxpatterns.sec01.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAggregatorService {
    private final ProductClient productClient;
    private final PromotionClient promotionClient;
    private final ReviewClient reviewClient;

    public Mono<ProductAggregator> aggregate(Integer id) {
        return Mono.zip(productClient.getProduct(id), promotionClient.getPromotion(id), reviewClient.getReview(id))
                .map(t -> toDto(t.getT1(), t.getT2(), t.getT3()));

    }

    private ProductAggregator toDto(ProductResponse product, PromotionResponse promotion, List<Review> reviews) {
        Price price = new Price();
        double amountSaved = product.getPrice() * promotion.getDiscount() / 100;
        double discountedPrice = product.getPrice() - amountSaved;
        price.setListPrice(product.getPrice());
        price.setAmountSaved(amountSaved);
        price.setDiscount(promotion.getDiscount());
        price.setDiscountedPrice(discountedPrice);
        price.setEndDate(promotion.getEndDate());

        return ProductAggregator.builder()
                .id(product.getId())
                .category(product.getCategory())
                .description(product.getDescription())
                .price(price)
                .reviews(reviews)
                .build();
    }
}
