package ru.test.payment.service;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.test.payment.event.RequestCreated;
import ru.test.payment.web.Payment;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class PaymentDao {

    private static final String INSERT_QUERY =
            "insert into payment (id, person_id, stock_code, stock_count, request_date)" +
                    " values (:id, :personId, :stockCode, :stockCount, :requestDate)";

    private static final String UPDATE_STATUS_QUERY = "update payment set status = :status where id = :requestId";

    private static final String SELECT_QUERY =
            "select id as request_id, stock_code, stock_count, request_date, status from payment" +
                    " where person_id = :personId";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PaymentDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void insert(RequestCreated request) {
        jdbcTemplate.update(INSERT_QUERY, new BeanPropertySqlParameterSource(request));
    }

    public void updateStatus(UUID requestId, String status) {
        jdbcTemplate.update(UPDATE_STATUS_QUERY, Map.of("status", status, "requestId", requestId));
    }

    public List<Payment> getPayments(UUID personId) {
        return jdbcTemplate.query(SELECT_QUERY, Map.of("personId", personId),
                new BeanPropertyRowMapper<>(Payment.class));
    }
}
