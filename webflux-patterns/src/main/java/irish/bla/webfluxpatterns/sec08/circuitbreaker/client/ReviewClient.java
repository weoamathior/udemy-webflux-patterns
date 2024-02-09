package irish.bla.webfluxpatterns.sec08.circuitbreaker.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import irish.bla.webfluxpatterns.sec08.circuitbreaker.dto.Review;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class ReviewClient {
    private final WebClient client;

    public ReviewClient(@Value("${sec08.review.service}") String baseUrl) {
        client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    // name from application.yaml config
    @CircuitBreaker(name = "review-service", fallbackMethod = "fallbackReview")
    public Mono<List<Review>> getReview(Integer id) {
        return client
                .get()
                .uri("{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                .bodyToFlux(Review.class)
                .collectList()
                .retry(5)
//                .retryWhen(Retry.fixedDelay(10, Duration.ofMillis(500)))
//                .retryWhen(Retry.backoff(10, Duration.ofMillis(500)))
                .timeout(Duration.ofMillis(300));

    }

    public Mono<List<Review>> fallbackReview(Integer id, Throwable t) {
        log.warn("fallbackReview called " + t.getMessage());
        return Mono.just(Collections.emptyList());
    }
}
