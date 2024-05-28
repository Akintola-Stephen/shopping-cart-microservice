package com.microservice.orderservice.service.impl;

import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.payload.request.OrderRequest;
import com.microservice.orderservice.payload.request.PaymentRequest;
import com.microservice.orderservice.payload.response.OrderResponse;
import com.microservice.orderservice.payload.response.PaymentResponse;
import com.microservice.orderservice.repository.OrderRepository;
import com.microservice.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.extern.log4j.Log4j2;

import java.time.Instant;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        log.info("Place order request...");

        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .build();

        order = orderRepository.save(order);

        log.info("OrderServiceImpl | placeOrder | Calling Payment Service to complete the payment");

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .amount(orderRequest.getTotalAmount())
                .build();

        String orderStatus = null;

        try {
            log.info("OrderServiceImpl | placeOrder | Calling Payment Service to complete the payment");
            orderStatus = "PLACED";
        }
        catch (Exception e) {
            log.error("OrderServiceImpl | placeOrder | Exception caught in OrderServiceImpl");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        log.info("Order placed successfully with oder Id: {}", order.getId());

        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        return null;
    }
}
