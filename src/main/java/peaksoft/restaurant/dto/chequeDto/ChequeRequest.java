package peaksoft.restaurant.dto.chequeDto;

import java.util.List;

public record ChequeRequest(
        List<Long> menuiesIdList
) {
}