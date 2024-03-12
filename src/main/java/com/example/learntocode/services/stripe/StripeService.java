//package com.example.learntocode.services.stripe;
//
//import com.stripe.model.checkout.Session;
//import com.stripe.param.checkout.SessionCreateParams;
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class StripeService {
//    @Value("${stripe.key.public}")
//    private String publicKey;
//    @Value("${stripe.key.secret}")
//    private String secretKey;
//
//
//    @SneakyThrows
//    Session createCheckoutSession() {
//        SessionCreateParams params =
//                SessionCreateParams.builder()
//                        .setSuccessUrl("http://localhost:8080/success")
//                        .addLineItem(
//                                SessionCreateParams.LineItem.builder()
//                                        .setPrice("price_1MotwRLkdIwHu7ixYcPLm5uZ")
//                                        .setQuantity(2L)
//                                        .build()
//                        )
//                        .setMode(SessionCreateParams.Mode.PAYMENT)
//                        .build();
//
//        Session session = Session.create(params);
//
//        return session;
//    }
//
//}
