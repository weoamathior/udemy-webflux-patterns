package irish.bla.webfluxpatterns.sec01.dto;

import lombok.Data;

@Data
public class Review {
    private Integer id;
    private String user;
    private Integer rating;
    private String comment;
}
