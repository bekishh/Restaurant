package peaksoft.restaurant.repository.dao;

import peaksoft.restaurant.dto.stopListDto.StopListPagination;
import peaksoft.restaurant.dto.stopListDto.StopListResponse;

import java.util.List;

public interface StopListDao {
    List<StopListResponse> getAllStopLists(Long restId);

    StopListResponse getStopListById(Long stopListId);

    StopListPagination getAll(int page, int pageSize);
}
