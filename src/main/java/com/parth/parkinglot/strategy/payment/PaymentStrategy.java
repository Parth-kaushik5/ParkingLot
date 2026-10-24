package com.parth.parkinglot.strategy.payment;

import com.parth.parkinglot.entity.Payment;
import com.parth.parkinglot.entity.PaymentMethod;

public interface PaymentStrategy {
    PaymentMethod getPaymentMethod();
    Payment pay(String ticketId, long amountInRupees);
}
