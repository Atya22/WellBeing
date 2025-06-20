package com.aytaj.wellbeing.controller;


import com.aytaj.wellbeing.service.payment.PaymentService;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity <Map <String, String>> createPayment(@RequestParam Long amount){
        try {
            PaymentIntent paymentIntent = paymentService.createPayment(amount, "azn");
            Map<String, String> response = new HashMap<>();
            response.put("paymentIntentId", paymentIntent.getId());
            response.put("clientSecret", paymentIntent.getClientSecret());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to create payment " + e.getMessage()));
        }
    }
}
