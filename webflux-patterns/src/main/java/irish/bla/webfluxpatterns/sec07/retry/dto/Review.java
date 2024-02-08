package irish.bla.webfluxpatterns.sec07.retry.dto;

import lombok.Data;

@Data
public class Review {
    private Integer id;
    private String user;
    private Integer rating;
    private String comment;
}
