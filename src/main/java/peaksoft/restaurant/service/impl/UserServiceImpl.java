package peaksoft.restaurant.service.impl;

import peaksoft.restaurant.dto.authDto.SignUpRequest;
import peaksoft.restaurant.dto.SimpleResponse;
import peaksoft.restaurant.dto.userDto.UserResponse;
import peaksoft.restaurant.dto.userDto.UserResponsePagination;
import peaksoft.restaurant.entity.Restaurant;
import peaksoft.restaurant.entity.User;
import peaksoft.restaurant.enums.Role;
import peaksoft.restaurant.exception.AlreadyException;
import peaksoft.restaurant.exception.BadCredentialException;
import peaksoft.restaurant.exception.BadRequestException;
import peaksoft.restaurant.exception.NotFoundException;
import peaksoft.restaurant.repository.RestaurantRepository;
import peaksoft.restaurant.repository.UserRepository;
import peaksoft.restaurant.repository.dao.impl.UserDaoImpl;
import peaksoft.restaurant.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDaoImpl userDaoImpl;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponsePagination getAll(int currentPage,int pageSize) {
        log.info("User list successfully exit");
        return userDaoImpl.getAllUsers(currentPage,pageSize);
    }

    @Override
    public UserResponse getById(Long userId) {
        if (!userRepository.existsById(userId)) {
            log.error("User with id: " + userId + " is not found");
            throw new NotFoundException(
                    "User with id: " + userId + " is not found!");
        }
        log.info(String.format("User with id:%s successfully exit", userId));
        return userDaoImpl.getUserById(userId);
    }

    @Override
    public SimpleResponse deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            log.error("User with id: " + userId + " is not found");
            throw new NotFoundException(
                    "User with id: " + userId + " is not found!");
        }
        log.info(String.format("User with id:%s successfully deleted", userId));
        userRepository.deleteById(userId);
        return new SimpleResponse(
                HttpStatus.OK,
                String.format("User with id:%s successfully deleted", userId)
        );
    }

    @Override
    public SimpleResponse updateUser(Long userId, SignUpRequest signUpRequest) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(
                        String.format("User with id:%s not found", userId)
                )
        );
        user.setFirstName(signUpRequest.firstName());
        user.setLastName(signUpRequest.lastName());
        user.setDateOfBirth(signUpRequest.dateOfBirth());
        user.setEmail(signUpRequest.email());
        user.setPassword(signUpRequest.password());
        user.setPhoneNumber(signUpRequest.firstName());
        user.setExperience(signUpRequest.experience());
        user.setRole(signUpRequest.role());
        userRepository.save(user);
        log.info(String.format("User with id:%s successfully updated", userId));
        return new SimpleResponse(
                HttpStatus.OK,
                String.format("User with id:%s successfully updated", userId)
        );
    }

    @Override
    public SimpleResponse saveUser(Long restaurantId, SignUpRequest signUpRequest) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> {
            String message = "Not found restaurant with id: " + restaurantId;
            log.error(message);
            return new NotFoundException(message);
        });
        if (userRepository.numberOfEmployees(restaurant.getId()) > 15) {
            throw new BadCredentialException("No vacancy! Try later!");
        }
        User user = new User();
        user.setFirstName(signUpRequest.firstName());
        user.setLastName(signUpRequest.lastName());
        user.setDateOfBirth(signUpRequest.dateOfBirth());
        user.setEmail(signUpRequest.email());
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));
        user.setPhoneNumber(signUpRequest.phoneNumber());
        restaurant.setNumberOfEmployees(userRepository.numberOfEmployees(user.getId()));
        user.setRole(signUpRequest.role());


        int experience = Period.between( signUpRequest.experience(),LocalDate.now()).getYears();
        int age = Period.between(signUpRequest.dateOfBirth(), LocalDate.now()).getYears();

        if (signUpRequest.role() == Role.CHEF) {
            if (age < 25 || age > 45) {
                throw new BadCredentialException("For CHEF candidate's age - %s doesn't match the company requirements!".formatted(age));
            }
            if (experience < 2) {
                log.error("The candidate's work experience is low!");
                throw new BadCredentialException("The candidate's work experience is low!");
            }

        } else if (signUpRequest.role() == Role.WAITER) {
            if (age < 18 || age > 30) {
                log.error("For WAITER candidate's age - %s doesn't match the compan requirements!".formatted(age));
                throw new BadCredentialException("For WAITER candidate's  age - %s doesn't match the company requirements!".formatted(age));
            }

            if (experience < 1) {
                log.error("The candidate's work experience is low!");
                throw new BadCredentialException("The candidate's work experience is low!");
            }
        } else if (signUpRequest.role() == Role.ADMIN) {
            log.error("Saving ADMIN is not allowed!");
            throw new BadCredentialException("Saving ADMIN is not allowed!");
        } else {
            log.error("Invalid role type!");
            throw new BadRequestException("Invalid role type!");
        }
        user.setExperience(signUpRequest.experience());
        user.setRestaurant(restaurant);
        restaurant.getUserList().add(user);
        userRepository.save(user);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("User successfully saved to Restaurant with id: %s ", restaurantId))
                .build();
    }


    @Override
    public SimpleResponse acceptOrRejectUser(Long userId, String word, Long restaurantId) {


        User user = userRepository.findById(userId).orElseThrow(() -> {
            String message = "Not found user with id: " + userId;
            log.error(message);
            return new NotFoundException(message);
        });

        if (user.getRestaurant() != null) {
            throw new AlreadyException("This user is already works in a restaurant!");
        }

        if (word.equalsIgnoreCase("Accept") || word.equalsIgnoreCase("a") ) {
            if (userRepository.numberOfEmployees(1L) > 15) {
                throw new BadCredentialException("No vacancy! Try later!");
            }

            LocalDate dateOfBirth = user.getDateOfBirth();
            LocalDate currentZoneDate = LocalDate.now();

            int age = Period.between(dateOfBirth, currentZoneDate).getYears();

            if (user.getRole() == Role.CHEF) {
                if (age < 25 || age > 45) {
                    throw new BadCredentialException("For CHEF candidate's age - %s doesn't match the company requirements!".formatted(age));
                }
            } else if (user.getRole() == Role.WAITER) {

                if (age < 18 || age > 30) {
                    throw new BadCredentialException("For WAITER candidate's  age - %s doesn't match the company requirements!".formatted(age));
                }
            } else if (user.getRole() == Role.ADMIN) {
                throw new BadCredentialException("Saving ADMIN is not allowed!");
            } else {
                throw new BadRequestException("Invalid role type!");
            }
            Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> {
                String message = "Not found restaurant with id: " + 1;
                log.error(message);
                return new NotFoundException(message);
            });

            user.setRestaurant(restaurant);
            userRepository.save(user);
            log.info(String.format("User with id: %s successfully hired at a restaurant with name : %s!", user.getId(), restaurant.getName()));

            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message(String.format("User with id: %s successfully hired at a restaurant with name : %s!", user.getId(), restaurant.getName()))
                    .build();
        } else if (word.equalsIgnoreCase("Reject")) {
            userRepository.delete(user);
            log.info(String.format("User with id: %s unfortunately was rejected by admin!", user.getId()));
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message(String.format("User with id: %s was rejected by admin!", user.getId()))
                    .build();
        }
        return null;
    }
}