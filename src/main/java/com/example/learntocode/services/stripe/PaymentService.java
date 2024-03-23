package com.example.learntocode.services.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PaymentService {

    @Value("${stripe.key.secret}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public String createCheckoutSession(Long amount) throws StripeException {
        SessionCreateParams.LineItem lineItem = createLineItem(amount);
        List<SessionCreateParams.LineItem> lineItems = Collections.singletonList(lineItem);

        SessionCreateParams params = createSessionParams(lineItems);
        Session session = Session.create(params);

        return session.getUrl();
    }

    private SessionCreateParams.LineItem createLineItem(Long amount) {
        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName("Donation")
                        .build();

        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("bgn")
                        .setUnitAmount(amount * 100)
                        .setProductData(productData)
                        .build();

        return SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                .setPriceData(priceData)
                .build();
    }

    private SessionCreateParams createSessionParams(List<SessionCreateParams.LineItem> lineItems) {
        return SessionCreateParams.builder()
                .setSuccessUrl("https://bonda.tech/success")
                .setCancelUrl("https://bonda.tech/cancel")
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .addAllLineItem(lineItems)
                .build();
    }

}
