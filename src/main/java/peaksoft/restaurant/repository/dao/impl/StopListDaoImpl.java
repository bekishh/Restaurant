package peaksoft.restaurant.repository.dao.impl;

import peaksoft.restaurant.dto.stopListDto.StopListPagination;
import peaksoft.restaurant.dto.stopListDto.StopListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import peaksoft.restaurant.repository.dao.StopListDao;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StopListDaoImpl implements StopListDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<StopListResponse> getAllStopLists(Long restId) {
        String sql = """
                SELECT
                id,
                date,
                reason,
                menu_id
                FROM stop_lists WHERE id = ?
                """;
        return jdbcTemplate.query(sql, (resultSet, _) -> new StopListResponse(
                resultSet.getLong("id"),
                LocalDate.parse(resultSet.getString("date")),
                resultSet.getString("reason"),
                resultSet.getLong("menu_id")
        ), restId);
    }

    @Override
    public StopListResponse getStopListById(Long stopListId) {
        String sql = """
                SELECT 
                id,
                date,
                reason,
                menu_id 
                FROM stop_lists WHERE id = ?
                """;
        return jdbcTemplate.queryForObject(sql, (resultSet, _) -> new StopListResponse(
                resultSet.getLong("id"),
                LocalDate.parse(resultSet.getString("date")),
                resultSet.getString("reason"),
                resultSet.getLong("menu_id")
        ), stopListId);
    }

    @Override
    public StopListPagination getAll(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        String sql = """
                SELECT
                id,
                date,
                reason,
                menu_id
                FROM stop_lists
                LIMIT ? OFFSET ?
                """;
        List<StopListResponse> stopListResponses = jdbcTemplate.query(sql, (resultSet, rowNumber) ->
                        new StopListResponse(
                                resultSet.getLong("id"),
                                LocalDate.parse(resultSet.getString("date")),
                                resultSet.getString("reason"),
                                resultSet.getLong("menu_id")
                        ),
                pageSize, offset
        );
        return StopListPagination
                .builder()
                .page(page)
                .pageSize(pageSize)
                .stopListResponses(stopListResponses)
                .build();
    }
}