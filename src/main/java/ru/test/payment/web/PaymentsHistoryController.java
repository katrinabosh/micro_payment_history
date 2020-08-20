package ru.test.payment.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.test.payment.service.PaymentDao;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
public class PaymentsHistoryController {

    private final PaymentDao paymentDao;

    public PaymentsHistoryController(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    @GetMapping(value = "/payments/{personId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Payment> getStockInfo(@PathVariable("personId") String personId) {
        return paymentDao.getPayments(UUID.fromString(personId));
    }


}
