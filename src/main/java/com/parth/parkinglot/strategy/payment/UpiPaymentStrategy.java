package com.parth.parkinglot.strategy.payment;

import com.parth.parkinglot.entity.Payment;
import com.parth.parkinglot.entity.PaymentMethod;
import com.parth.parkinglot.entity.PaymentStatus;

public class UpiPaymentStrategy implements PaymentStrategy {
    @Override public PaymentMethod getPaymentMethod() { return PaymentMethod.UPI; }
    @Override public Payment pay(String ticketId, long amount) {
        return new Payment("PAY-" + ticketId, getPaymentMethod(), PaymentStatus.SUCCESSFUL, amount, System.currentTimeMillis());
    }
}
