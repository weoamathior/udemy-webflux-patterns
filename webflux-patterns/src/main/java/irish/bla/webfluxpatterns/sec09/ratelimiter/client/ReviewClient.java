package irish.bla.webfluxpatterns.sec09.ratelimiter.client;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import irish.bla.webfluxpatterns.sec09.ratelimiter.dto.Review;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
@Slf4j
@Service
public class ReviewClient {
    private final WebClient client;

    public ReviewClient(@Value("${sec09.review.service}") String baseUrl) {
        client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @RateLimiter(name = "review-service", fallbackMethod = "fallback")
    public Mono<List<Review>> getReview(Integer id) {
        return client
                .get()
                .uri("{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                .bodyToFlux(Review.class)
                .collectList();

    }

    public Mono<List<Review>> fallback(Integer id, Throwable t) {
        log.info("too many calls == $$$! " + t.getMessage());
        return Mono.just(Collections.emptyList());
    }
}
