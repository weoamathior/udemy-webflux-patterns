package irish.bla.webfluxpatterns.sec08.circuitbreaker.dto;

import lombok.Data;

@Data
public class Product {

    private Integer id;
    private String category;
    private String description;
    private Integer price;
}
