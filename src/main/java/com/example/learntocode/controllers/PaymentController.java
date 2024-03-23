package com.example.learntocode.controllers;

import com.example.learntocode.services.stripe.PaymentService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping("/api/donate")
    public ResponseEntity<Map<String, String>> createPaymentIntent(@RequestBody Map<String, Integer> amount) throws StripeException {
        String url = paymentService.createCheckoutSession(Long.valueOf(amount.get("amount")));
        return ResponseEntity.ok(Map.of("url", url));
    }

}