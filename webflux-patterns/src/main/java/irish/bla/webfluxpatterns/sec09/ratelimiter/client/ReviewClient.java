package irish.bla.webfluxpatterns.sec09.ratelimiter.client;

import irish.bla.webfluxpatterns.sec09.ratelimiter.dto.Review;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Service
public class ReviewClient {
    private final WebClient client;

    public ReviewClient(@Value("${sec09.review.service}") String baseUrl) {
        client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

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
                .timeout(Duration.ofMillis(300))
                .onErrorReturn(Collections.emptyList());

    }
}
