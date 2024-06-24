package peaksoft.restaurant.service;

import peaksoft.restaurant.dto.stopListDto.StopListRequest;
import peaksoft.restaurant.dto.SimpleResponse;
import peaksoft.restaurant.dto.stopListDto.StopListPagination;
import peaksoft.restaurant.dto.stopListDto.StopListResponse;
import java.util.List;

public interface StopListService {
    SimpleResponse saveStopList(Long menuId, StopListRequest stopListRequest);
    StopListResponse getStopListById(Long stopListId);
    List<StopListResponse> getAllStopLists(Long restId);
    SimpleResponse deleteStopList(Long stopListId);
    SimpleResponse updateStopList(Long stopListId,StopListRequest stopListRequest);
    StopListPagination getAll (int page, int pageSize);
}