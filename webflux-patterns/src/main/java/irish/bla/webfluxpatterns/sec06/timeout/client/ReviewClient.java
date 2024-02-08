package irish.bla.webfluxpatterns.sec06.timeout.client;

import irish.bla.webfluxpatterns.sec06.timeout.dto.Review;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Collections;
import java.util.List;

@Service
public class ReviewClient {
    private final WebClient client;

    public ReviewClient(@Value("${sec06.review.service}") String baseUrl) {
        client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<List<Review>> getReview(Integer id) {
        return client
                .get()
                .uri("{id}", id)
                .retrieve()
                .bodyToFlux(Review.class)
                .collectList()
                .timeout(Duration.ofMillis(500))
                .onErrorReturn(Collections.emptyList());

    }
}
