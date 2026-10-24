package com.parth.parkinglot.entity;

public class Payment {
    private final String transactionId;
    private final PaymentMethod method;
    private final PaymentStatus status;
    private final long amount;
    private final long paidAtMillis;

    public Payment(String transactionId, PaymentMethod method, PaymentStatus status, long amount, long paidAtMillis) {
        this.transactionId = transactionId;
        this.method = method;
        this.status = status;
        this.amount = amount;
        this.paidAtMillis = paidAtMillis;
    }
    public String getTransactionId() { return transactionId; }
    public PaymentMethod getMethod() { return method; }
    public PaymentStatus getStatus() { return status; }
    public long getAmount() { return amount; }
    public long getPaidAtMillis() { return paidAtMillis; }
}
