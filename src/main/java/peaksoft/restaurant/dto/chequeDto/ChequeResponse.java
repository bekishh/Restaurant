package peaksoft.restaurant.dto.chequeDto;

import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
public record ChequeResponse(
        Long id,
        String waiterFullName,
        List<String> items,
        BigDecimal averagePrice,
        BigDecimal total,
        LocalDate date) {
    public ChequeResponse(Long id, String waiterFullName, List<String> items, BigDecimal averagePrice, BigDecimal total, LocalDate date) {
        this.id = id;
        this.waiterFullName = waiterFullName;
        this.items = items;
        this.averagePrice = averagePrice;
        this.total = total;
        this.date = date;
    }
}