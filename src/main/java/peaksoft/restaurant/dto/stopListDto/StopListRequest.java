package peaksoft.restaurant.dto.stopListDto;

import java.time.LocalDate;

public record StopListRequest(
        LocalDate date,
        String reason
) {
}