package irish.bla.webfluxpatterns.sec10.bulkhead.dto;

import lombok.Data;

@Data
public class Review {
    private Integer id;
    private String user;
    private Integer rating;
    private String comment;
}
