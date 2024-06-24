package peaksoft.restaurant.api;

import peaksoft.restaurant.dto.chequeDto.ChequeRequest;
import peaksoft.restaurant.dto.chequeDto.ChequeResponse;
import peaksoft.restaurant.dto.chequeDto.ChequeResponsePagination;
import peaksoft.restaurant.dto.SimpleResponse;
import peaksoft.restaurant.service.ChequeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/cheque")
@RequiredArgsConstructor
@Tag(name = "Cheque-API")
public class ChequeApi {
    private final ChequeService chequeService;

    @PreAuthorize("hasAuthority('WAITER')")
    @PostMapping("/save")
    public SimpleResponse save(@RequestBody ChequeRequest chequeRequest){
        return chequeService.save(chequeRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public ChequeResponsePagination getAll(@RequestParam int currentPage, @RequestParam int pageSize){
        return chequeService.findAll(currentPage,pageSize);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getById")
    public ChequeResponse getById(@RequestParam Long chequeId){
        return chequeService.findById(chequeId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update")
    public SimpleResponse update(@RequestParam Long chequeId ,@RequestBody ChequeRequest request){
        return chequeService.update(chequeId,request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete")
    public SimpleResponse delete(@RequestParam Long chequeId ){
        return chequeService.delete(chequeId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getWaiterPrice")
    public BigDecimal getWaiter(@RequestParam Long userId){
        return chequeService.waitersPrice(userId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getRestAvg")
    public BigDecimal getRestAvg(@RequestParam Long restaurantId){
        return chequeService.dayPrice(restaurantId);
    }
}