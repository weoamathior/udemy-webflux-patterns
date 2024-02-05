package irish.bla.webfluxpatterns.sec04.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ProductResponse {
    private Integer id;
    private String category;
    private String description;
    private Integer price;
}
