package org.example.service.strategy;

public class PaymentFactory {
    public static IPaymentStrategy getInstance(String type)
    {
        return switch (type) {
            case "gateway" -> new ApiGateWayPayments();
            case "crypto" -> new CryptoPayments();
            default -> null;
        };
    }
}
