package peaksoft.restaurant.repository.dao.impl;

import peaksoft.restaurant.dto.categoryDto.CategoryPaginationResponse;
import peaksoft.restaurant.dto.categoryDto.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import peaksoft.restaurant.repository.dao.CategoryDao;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryJDBCTemplate implements CategoryDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public CategoryPaginationResponse getAllCategory(int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        String sql = """
                SELECT id,
                name FROM categories
                LIMIT ? OFFSET ?
                """;
        List<CategoryResponse> categoryResponseList = jdbcTemplate.query(sql, (resultSet, rowNumber) ->
                        new CategoryResponse(
                                resultSet.getLong("id"),
                                resultSet.getString("name")
                        )
                , currentPage, offset);
        return CategoryPaginationResponse.builder()
                .currentPage(currentPage)
                .pageSize(pageSize)
                .categoryResponseList(categoryResponseList).build();
    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        String sql = "SELECT id, name FROM categories WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            return new CategoryResponse(
                    rs.getLong("id"),
                    rs.getString("name"));
        }, categoryId);
    }
}