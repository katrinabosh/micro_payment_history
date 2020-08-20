package ru.test.payment.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import ru.test.payment.Application;
import ru.test.payment.event.AccountChanged;
import ru.test.payment.event.RequestCreated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class EventHandlerTest {

    @Autowired
    private EventHandler eventHandler;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void listenRequestCreated() {

    }

    @Test
    public void listenBalanceRequestCanceled() {
    }

    @Test
    public void listenStocksRequestCanceled() {
    }

    @Test
    public void listenAccountChanged() {
        UUID requestId = UUID.randomUUID();
        UUID personId = UUID.randomUUID();
        eventHandler.listenRequestCreated(new RequestCreated(requestId, personId, "MMM", 12, LocalDateTime.now()));

        eventHandler.listenAccountChanged(new AccountChanged(requestId, personId, BigDecimal.valueOf(500_000)));

        var result = jdbcTemplate.queryForMap(" select * from payment where id = ?", requestId);
        //System.out.println(result);

        assertTrue(result.size() > 0);
        assertEquals(personId, result.get("person_id"));
        assertEquals("MMM", result.get("stock_code"));
        assertEquals(BigDecimal.valueOf(12), result.get("stock_count"));
        assertNotNull(result.get("request_date"));
        assertEquals("Исполнен на сумму 500000", result.get("status"));


    }
}