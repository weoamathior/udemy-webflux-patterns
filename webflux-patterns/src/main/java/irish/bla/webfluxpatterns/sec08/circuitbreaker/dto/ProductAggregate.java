package irish.bla.webfluxpatterns.sec08.circuitbreaker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAggregate {

    private Integer id;
    private String category;
    private String description;
    private List<Review> reviews;

}
