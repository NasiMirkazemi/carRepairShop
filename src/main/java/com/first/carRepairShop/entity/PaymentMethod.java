package com.first.carRepairShop.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

public enum PaymentMethod {
    CASH(true),
    CREDIT_CARD(true),
    DEBIT_CARD(true),
    BANK_TRANSFER(false),
    PAYPAL(false);
    private final boolean instant;

    PaymentMethod(boolean instant) {
        this.instant = instant;
    }

    public boolean isInstant() {
        return instant;
    }
}
