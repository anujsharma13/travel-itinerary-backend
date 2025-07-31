package org.example.service.strategy;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.example.dto.PaymentRequest;
import org.example.dto.PaymentResponse;
import org.example.model.entity.Payment;
import org.example.model.entity.User;
import org.example.repository.PaymentRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiGateWayPayments implements IPaymentStrategy {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    private String stripeSecretKey="";

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest, String userName) {
        try {
            log.info("Stripe secret key loaded: {}", stripeSecretKey != null ? "****" + stripeSecretKey.substring(stripeSecretKey.length() - 4) : "NULL");
            if (stripeSecretKey == null || stripeSecretKey.trim().isEmpty()) {
                log.error("Stripe secret key is null or empty");
                return PaymentResponse.failed("Stripe API key not configured");
            }
            
            Stripe.apiKey = stripeSecretKey;

            long amountInCents = paymentRequest.getAmount().multiply(new BigDecimal("100")).longValue();

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amountInCents)
                    .setCurrency(paymentRequest.getCurrency().toLowerCase())
                    .setPaymentMethod(paymentRequest.getPaymentMethodId())
                    .setDescription(paymentRequest.getDescription())
                    .setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.MANUAL)
                    .setConfirm(true)
                    .setReturnUrl("https://website/return")
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            log.info("Payment processed successfully. Payment Intent ID: {}", paymentIntent.getId());

            savePayment(paymentRequest, userName);

            return PaymentResponse.builder()
                    .paymentId(paymentIntent.getId())
                    .status(paymentIntent.getStatus())
                    .amount(paymentRequest.getAmount())
                    .currency(paymentRequest.getCurrency())
                    .paymentMethod("stripe_gateway")
                    .description(paymentRequest.getDescription())
                    .clientSecret(paymentIntent.getClientSecret())
                    .success("succeeded".equals(paymentIntent.getStatus()) || "requires_action".equals(paymentIntent.getStatus()))
                    .build();

        } catch (StripeException e) {
            log.error("Stripe payment failed: {}", e.getMessage());
            return PaymentResponse.failed("Payment failed: " + e.getUserMessage());
        } catch (Exception e) {
            log.error("Payment processing failed: {}", e.getMessage());
            return PaymentResponse.failed("Payment processing failed: " + e.getMessage());
        }
    }

    private void savePayment(PaymentRequest paymentRequest, String userName) throws BadRequestException {
        try {
            int x=0;
            int y=1;
            System.out.println(y/x);
            User user = userRepository.findByUsername(userName).orElse(null);
            if (user == null)
                throw new BadRequestException();
            Payment payment = new Payment();
            payment.setPaymentMethodId(paymentRequest.getPaymentMethodId());
            payment.setPaymentType(paymentRequest.getPaymentType());
            payment.setUser(user);
            payment.setAmount(paymentRequest.getAmount());
            payment.setCurrency(paymentRequest.getCurrency());
            payment.setSubscriptionPlan(paymentRequest.getSubscriptionPlan());
            paymentRepository.save(payment);
        }catch (Exception e)
        {
            System.out.println(e.getStackTrace());
        }
    }
}






