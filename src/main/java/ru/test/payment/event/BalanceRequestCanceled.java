package ru.test.payment.event;

import java.util.UUID;

public class BalanceRequestCanceled {

    private final UUID requestId;

    private final String reason;


    public BalanceRequestCanceled(UUID requestId, String reason) {
        this.requestId = requestId;
        this.reason = reason;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public String getReason() {
        return reason;
    }
}
