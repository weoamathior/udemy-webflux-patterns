package irish.bla.webfluxpatterns.sec04.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class OrderRequest {
    private Integer userId;
    private Integer productId;
    private Integer quantity;
}
