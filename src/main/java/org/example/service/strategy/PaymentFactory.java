package org.example.service.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentFactory {
    
    private final ApiGateWayPayments apiGateWayPayments;
    private final CryptoPayments cryptoPayments;
    
    public IPaymentStrategy getInstance(String type) {
        return switch (type) {
            case "gateway" -> apiGateWayPayments;
            case "crypto" -> cryptoPayments;
            default -> null;
        };
    }
}
