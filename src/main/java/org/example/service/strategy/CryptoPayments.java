package org.example.service.strategy;

public class CryptoPayments implements IPaymentStrategy{
    @Override
    public boolean pay() {
        return false;
    }
}
