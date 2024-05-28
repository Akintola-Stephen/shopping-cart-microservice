package com.microservice.orderservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

public class OrderResponse {
    private long orderId;
    private Instant orderDate;
    private String orderStatus;
    private long amount;
    private ProductDetails productDetails;
    private PaymentDetails paymentDetails;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductDetails {
        private Long productId;
        private String productName;
        private Long quantity;
        private Long price;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PaymentDetails {
        private long paymentId;
        private String paymentStatus;
        private Instant paymentDate;
    }
}
