package ru.test.payment.service;

import org.springframework.stereotype.Service;
import ru.test.payment.event.AccountChanged;
import ru.test.payment.event.BalanceRequestCanceled;
import ru.test.payment.event.RequestCreated;
import ru.test.payment.event.StocksRequestCanceled;

@Service
public class EventHandler {

    private final PaymentDao paymentDao;

    public EventHandler(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    public void listenRequestCreated(RequestCreated event) {
        paymentDao.insert(event);
    }

    public void listenBalanceRequestCanceled(BalanceRequestCanceled event) {
        paymentDao.updateStatus(event.getRequestId(), "Отказ: " + event.getReason());
    }

    public void listenStocksRequestCanceled(StocksRequestCanceled event) {
        paymentDao.updateStatus(event.getRequestId(), "Отказ: " + event.getReason());
    }

    public void listenAccountChanged(AccountChanged event) {
        paymentDao.updateStatus(event.getRequestId(), "Исполнен на сумму " + event.getSum().toPlainString());
    }

}
