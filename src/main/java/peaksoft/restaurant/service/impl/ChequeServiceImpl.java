package peaksoft.restaurant.service.impl;


import peaksoft.restaurant.dto.chequeDto.ChequeRequest;
import peaksoft.restaurant.dto.chequeDto.ChequeResponse;
import peaksoft.restaurant.dto.chequeDto.ChequeResponsePagination;
import peaksoft.restaurant.dto.SimpleResponse;
import peaksoft.restaurant.entity.Cheque;
import peaksoft.restaurant.entity.Menu;
import peaksoft.restaurant.entity.User;
import peaksoft.restaurant.exception.BadRequestException;
import peaksoft.restaurant.exception.NotFoundException;
import peaksoft.restaurant.repository.ChequeRepository;
import peaksoft.restaurant.repository.MenuRepository;
import peaksoft.restaurant.repository.UserRepository;
import peaksoft.restaurant.repository.dao.impl.ChequesDaoImpl;
import peaksoft.restaurant.service.ChequeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChequeServiceImpl implements ChequeService {

    private final ChequeRepository chequeRepo;
    private final ChequesDaoImpl chequesDaoImpl;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    @Override
    public ChequeResponsePagination findAll(int currentPage, int pageSize) {
        return chequesDaoImpl.getAllCheques(currentPage, pageSize);
    }

    @Override
    public SimpleResponse save(ChequeRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            log.error("User with this email: %s not found".formatted(email));
            return new NotFoundException("User with this email: %s not found".formatted(email));
        });

        if (user.getRestaurant() == null) {
            throw new BadRequestException("This user is not working in this restaurant");
        }

        Cheque cheque = new Cheque();
        cheque.setCreatedAt(LocalDate.now());
        cheque.setUser(user);
        List<Menu> menuItems = new ArrayList<>();
        for (Long menuItemId : request.menuiesIdList()) {
            Menu menu = menuRepository.findById(menuItemId).orElseThrow(() -> new NotFoundException("Not found"));
            if (menu.getStopList() != null) {
                throw new BadRequestException("This product is already in stopList");
            }
            menuItems.add(menu);
        }
        cheque.setMenuList(menuItems);
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Menu m : menuItems) {
            totalPrice = totalPrice.add(m.getPrice());
        }
        cheque.setPriceAvg(totalPrice);
        chequeRepo.save(cheque);
        log.info("Cheque is successfully saved");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Cheque successfully saved")
                .build();
    }

    @Override
    public ChequeResponse findById(Long id) {
        return chequesDaoImpl.getById(id);
    }

    @Override
    public SimpleResponse update(Long chequeId, ChequeRequest request) {
        Cheque cheque = chequeRepo.findById(chequeId).orElseThrow(
                () -> {
                    log.error(String.format("Cheque with id: %s not found", chequeId));
                    return new NotFoundException(String.format("Cheque with id: %s not found", chequeId));
                }
        );
        List<Menu> menuList = new ArrayList<>();
        for (Long menuId : request.menuiesIdList()) {
            Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new NotFoundException("Not found"));
            if (menu.getStopList() != null) {
                throw new BadRequestException("This product is already in stopList");
            }
            menuList.add(menu);
        }
        cheque.setMenuList(menuList);
        chequeRepo.save(cheque);
        return new SimpleResponse(
                HttpStatus.OK,
                String.format("Cheque with id: %s successfully updated", chequeId)
        );
    }

    @Override
    public SimpleResponse delete(Long id) {
        chequeRepo.deleteChequeById(id).orElseThrow(() -> {
            log.error(String.format("Cheque with id: %s not found", id));
            return new NotFoundException(String.format("Cheque with id: %s not found", id));
        });
        return new SimpleResponse(
                HttpStatus.OK,
                String.format("Cheque with id: %s is successfully deleted",id)
        );
    }

    @Override
    public BigDecimal waitersPrice(Long userId) {
        return chequeRepo.getTopByCreatedAt(userId);
    }

    @Override
    public BigDecimal dayPrice(Long restaurantId) {
        return chequeRepo.avg(restaurantId);
    }
}