package peaksoft.restaurant.dto.chequeDto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Builder
@Getter
public class ChequeResponsePagination {
    private int currentPage;
    private int pageSize;
    private List<ChequeResponse> chequeResponseList;}