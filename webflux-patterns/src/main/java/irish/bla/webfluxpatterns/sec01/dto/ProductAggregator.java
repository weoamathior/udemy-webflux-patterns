package irish.bla.webfluxpatterns.sec01.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductAggregator {

    private Integer id;
    private String category;
    private String description;
    private Price price;
    private List<Review> reviews;

}
