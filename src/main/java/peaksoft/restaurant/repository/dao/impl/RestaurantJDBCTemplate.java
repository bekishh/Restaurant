package peaksoft.restaurant.repository.dao.impl;

import peaksoft.restaurant.dto.restaurantDto.RestaurantResponse;
import peaksoft.restaurant.enums.RestType;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import peaksoft.restaurant.repository.dao.RestaurantDao;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RestaurantJDBCTemplate implements RestaurantDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<RestaurantResponse> restaurantRowMapper = (resultSet, _) -> {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String location = resultSet.getString("location");

        String restTypeString = resultSet.getString("rest_type");
        RestType restType = (restTypeString != null) ? RestType.valueOf(restTypeString) : null;

        int numberOfEmployees = resultSet.getInt("numberOfEmployees");
        int service = resultSet.getInt("service");

        return new RestaurantResponse(id, name, location, restType, numberOfEmployees, service);
    };

    @Override
    public List<RestaurantResponse> getAllRestaurant() {
        String sql = """
                SELECT
                id,
                name,
                location,
                rest_type,
                number_of_employees as numberOfEmployees,
                service FROM restaurants
                """;

        return jdbcTemplate.query(sql, restaurantRowMapper);
    }

    @Override
    public RestaurantResponse getById(Long restaurantId) {
        String sql = """
                SELECT
                id,
                name,
                location,
                rest_type,
                number_of_employees as numberOfEmployees,
                service FROM restaurants WHERE id = ?
                """;
        return jdbcTemplate.queryForObject(sql, restaurantRowMapper, restaurantId);
    }
}