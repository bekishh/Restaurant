package peaksoft.restaurant.repository.dao;

import peaksoft.restaurant.dto.chequeDto.ChequeResponse;
import peaksoft.restaurant.dto.chequeDto.ChequeResponsePagination;

public interface ChequesDao {
    ChequeResponsePagination getAllCheques(int currentPage, int pageSize);

    ChequeResponse getById(Long chequeId);

}
