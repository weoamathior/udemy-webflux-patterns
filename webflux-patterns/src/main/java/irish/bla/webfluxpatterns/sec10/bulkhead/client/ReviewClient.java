package irish.bla.webfluxpatterns.sec10.bulkhead.client;

import irish.bla.webfluxpatterns.sec10.bulkhead.dto.Review;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
@Slf4j
@Service
public class ReviewClient {
    private final WebClient client;

    public ReviewClient(@Value("${sec10.review.service}") String baseUrl) {
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
                .collectList();

    }
}
