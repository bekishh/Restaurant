package peaksoft.restaurant.repository.dao.impl;

import peaksoft.restaurant.dto.chequeDto.ChequeResponse;
import peaksoft.restaurant.dto.chequeDto.ChequeResponsePagination;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import peaksoft.restaurant.repository.dao.ChequesDao;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChequesJDBCTemplate implements ChequesDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ChequeResponsePagination getAllCheques(int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        String sql = """
                        SELECT
                            ch.id AS id,
                            concat(u.first_name,' ',u.last_name) AS fullName,
                            array_agg(m.id) AS items,
                            sum(m.price) AS priceAvg,
                            sum(m.price+(m.price/100*r.service)) AS total,
                            ch.created_at AS date
                        FROM users AS u
                                 JOIN restaurants AS r ON u.restaurant_id = r.id
                                 LEFT JOIN cheques AS ch ON u.id = ch.user_id
                                 LEFT join cheques_menu_list cml ON ch.id = cml.cheque_list_id
                                 LEFT JOIN menus AS m ON cml.menu_list_id = m.id group by ch.id,u.first_name,u.last_name,r.service
                                 LIMIT ? OFFSET ?
                                 ;
                """;
        List<ChequeResponse> chequeResponseList = jdbcTemplate.query(sql, (resultSet, _) ->
                new ChequeResponse(
                        resultSet.getLong("id"),
                        resultSet.getString("fullName"),
                        Collections.singletonList(resultSet.getString("items")),
                        resultSet.getBigDecimal("priceAvg"),
                        resultSet.getBigDecimal("total"),
                        LocalDate.parse(resultSet.getString("date"))
                ), currentPage, offset);
        return ChequeResponsePagination
                .builder()
                .currentPage(currentPage)
                .pageSize(pageSize)
                .chequeResponseList(chequeResponseList)
                .build();
    }

    @Override
    public ChequeResponse getById(Long chequeId) {
        String sql = """
                SELECT 
                ch.id AS id,
                CONCAT(u.first_name,' ',u.last_name) AS fullName,
                array_agg(m.id) AS items,
                SUM(m.price) AS priceAvg,
                SUM(m.price+(m.price/100*r.service)) AS total,
                            ch.created_at AS date
                        FROM users AS u
                                 JOIN restaurants AS r ON u.restaurant_id = r.id
                                 LEFT JOIN cheques AS ch ON u.id = ch.user_id
                                 LEFT join cheques_menu_list cml ON ch.id = cml.cheque_list_id
                                 LEFT JOIN menus AS m ON cml.menu_list_id = m.id group by ch.id,u.first_name,u.last_name,r.service 
                                 HAVING ch.id = ?
                """;
        return jdbcTemplate.queryForObject(sql, (resultSet, _) -> {
            return new ChequeResponse(
                    resultSet.getLong("id"),
                    resultSet.getString("fullName"),
                    Collections.singletonList(resultSet.getString("items")),
                    resultSet.getBigDecimal("priceAvg"),
                    resultSet.getBigDecimal("total"),
                    LocalDate.parse(resultSet.getString("date")));
        }, chequeId);
    }
}