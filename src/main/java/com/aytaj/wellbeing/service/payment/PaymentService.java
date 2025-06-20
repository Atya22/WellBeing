package com.aytaj.wellbeing.service.payment;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;

@Service
public class PaymentService {
    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @PostConstruct
    public void initStripe(){
        Stripe.apiKey = stripeSecretKey;
    }

    public PaymentIntent createPayment(Long amount, String currency) throws Exception {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency(currency)
                .setCaptureMethod(PaymentIntentCreateParams.CaptureMethod.MANUAL) //reservation of money
                .build();

        return PaymentIntent.create(params);
    }

    public void capturePayment(String paymentIntendId) throws Exception {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntendId);
        paymentIntent.capture();
    }

}
