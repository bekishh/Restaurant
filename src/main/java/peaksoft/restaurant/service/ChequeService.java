package peaksoft.restaurant.service;

import peaksoft.restaurant.dto.chequeDto.ChequeRequest;
import peaksoft.restaurant.dto.chequeDto.ChequeResponse;
import peaksoft.restaurant.dto.chequeDto.ChequeResponsePagination;
import peaksoft.restaurant.dto.SimpleResponse;
import java.math.BigDecimal;

public interface ChequeService {
    ChequeResponsePagination findAll(int currentPage, int pageSize);

    SimpleResponse save(ChequeRequest request);

    ChequeResponse findById(Long id);

    SimpleResponse update(Long id, ChequeRequest request);

    SimpleResponse delete(Long id);
    BigDecimal waitersPrice(Long userId);
    BigDecimal dayPrice(Long restaurantId);
}
