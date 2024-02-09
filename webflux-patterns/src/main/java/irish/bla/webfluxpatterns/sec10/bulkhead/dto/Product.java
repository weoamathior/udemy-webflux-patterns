package irish.bla.webfluxpatterns.sec10.bulkhead.dto;

import lombok.Data;

@Data
public class Product {

    private Integer id;
    private String category;
    private String description;
    private Integer price;
}
