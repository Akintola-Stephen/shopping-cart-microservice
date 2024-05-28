package com.microservice.orderservice.payload.response;

import lombok.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private long paymentId;
    private String status;
    private long amount;
    private Instant paymentDate;
    private long orderId;
}
