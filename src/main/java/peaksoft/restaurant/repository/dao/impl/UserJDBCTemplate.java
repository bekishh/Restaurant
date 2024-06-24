package peaksoft.restaurant.repository.dao.impl;

import peaksoft.restaurant.dto.userDto.UserResponse;
import peaksoft.restaurant.dto.userDto.UserResponsePagination;
import peaksoft.restaurant.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import peaksoft.restaurant.repository.dao.UserDao;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserJDBCTemplate implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<UserResponse> userRowMapper = (resultSet, _) -> {
        Long id = resultSet.getLong("id");

        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");

        String dateOfBirthString = resultSet.getString("date_of_birth");
        LocalDate dateOfBirth = (dateOfBirthString != null) ? LocalDate.parse(dateOfBirthString) : null;

        String email = resultSet.getString("email");
        String phoneNumber = resultSet.getString("phone_number");

        String experienceString = resultSet.getString("experience");
        LocalDate experience = (experienceString != null) ? LocalDate.parse(experienceString) : null;

        String roleString = resultSet.getString("role");
        Role role = (roleString != null) ? Role.valueOf(roleString) : null;

        return new UserResponse(id, firstName, lastName, dateOfBirth, email, phoneNumber, experience, role);
    };


    @Override
    public UserResponsePagination getAllUsers(int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        String sql = """
                SELECT id,
                       first_name,
                       last_name,
                       date_of_birth,
                       email,
                       phone_number,
                       experience,
                       role
                FROM users
                LIMIT ? OFFSET ?
                """;
        List<UserResponse> userResponseList = jdbcTemplate.query(sql, (resultSet, rowNumber) -> {
            Role userRole = Role.valueOf(resultSet.getString("role"));
            String dateOfBirthStr = resultSet.getString("date_of_birth");
            LocalDate dateOfBirth = (dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) ?
                    LocalDate.parse(dateOfBirthStr) : null;

            String experienceStr = resultSet.getString("experience");
            LocalDate experienceDate = (experienceStr != null && !experienceStr.isEmpty()) ?
                    LocalDate.parse(experienceStr) : null;
            return new UserResponse(
                    resultSet.getLong("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    dateOfBirth,
                    resultSet.getString("email"),
                    resultSet.getString("phone_number"),
                    experienceDate,
                    userRole
            );

        }, currentPage, offset);

        return UserResponsePagination
                .builder()
                .currentPage(currentPage)
                .pageSize(pageSize)
                .userResponseList(userResponseList)
                .build();
    }

    @Override
    public UserResponse getUserById(Long userId) {
        String sql = """
                SELECT id,
                       first_name,
                       last_name,
                       date_of_birth,
                       email,
                       phone_number,
                       experience,
                       role
                FROM users WHERE id = ?
                """;
        return jdbcTemplate.queryForObject(sql, userRowMapper, userId);
    }
}