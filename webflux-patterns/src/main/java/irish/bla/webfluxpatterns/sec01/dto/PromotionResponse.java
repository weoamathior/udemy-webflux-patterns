package irish.bla.webfluxpatterns.sec01.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PromotionResponse {
    private Integer id;
    private String type;
    private  Double discount;
    private LocalDate endDate;

    public static PromotionResponse emptyPromption() {
        PromotionResponse promotionResponse = new PromotionResponse();
        promotionResponse.setId(-1);
        promotionResponse.setType("no promotion");
        promotionResponse.setDiscount(0.0);
        promotionResponse.setEndDate(LocalDate.EPOCH);
        return promotionResponse;
    }
}
