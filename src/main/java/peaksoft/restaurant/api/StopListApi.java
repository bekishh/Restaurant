package peaksoft.restaurant.api;

import peaksoft.restaurant.dto.stopListDto.StopListRequest;
import peaksoft.restaurant.dto.SimpleResponse;
import peaksoft.restaurant.dto.stopListDto.StopListPagination;
import peaksoft.restaurant.dto.stopListDto.StopListResponse;
import peaksoft.restaurant.service.StopListService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stopList")
@RequiredArgsConstructor
@Tag(name = "StopList-API")
public class StopListApi {

    private final StopListService stopListService;

    @PreAuthorize("hasAnyAuthority('CHEF','ADMIN')")
    @PostMapping("/save")
    public SimpleResponse save(@RequestParam Long menuId, @RequestBody StopListRequest stopListRequest){
        return stopListService.saveStopList(menuId,stopListRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public List<StopListResponse> getAll(@RequestParam Long restId){
        return stopListService.getAllStopLists(restId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getById")
    public StopListResponse getById(@RequestParam Long stopListId){
        return stopListService.getStopListById(stopListId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete")
    public SimpleResponse delete(@RequestParam Long stopListId){
        return stopListService.deleteStopList(stopListId);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update")
    public SimpleResponse update(@RequestParam Long stopListId,@RequestBody StopListRequest stopListRequest){
        return stopListService.updateStopList(stopListId,stopListRequest);
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public StopListPagination getAll (@RequestParam int page, @RequestParam int pageSize){
        return stopListService.getAll(page, pageSize);
    }
}