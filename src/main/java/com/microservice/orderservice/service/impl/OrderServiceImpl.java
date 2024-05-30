package com.microservice.orderservice.service.impl;

import com.microservice.orderservice.exception.OrderServiceCustomException;
import com.microservice.orderservice.entity.Order;
import com.microservice.orderservice.dto.request.OrderRequest;
import com.microservice.orderservice.dto.request.PaymentRequest;
import com.microservice.orderservice.dto.response.OrderResponse;
import com.microservice.orderservice.repository.OrderRepository;
import com.microservice.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.extern.log4j.Log4j2;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.microservice.orderservice.constants.ValidationMessages.ORDER_CONFIRMATION_MESSAGE;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

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
        } catch (Exception e) {
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
        log.info("OrderServiceImpl | getOrderDetails | Calling Get Order Details");
        Order order = orderRepository.findById((int) orderId)
                .orElseThrow(() -> new OrderServiceCustomException("Order not found for order Id" + orderId, "NOT FOUND", 404));


        log.info("OrderServiceImpl | getOrderDetails | Invoking Product service to fetch the product for id: {}\", order.getProductId()", order.getProductId());


        OrderResponse.ProductDetails productDetails = OrderResponse.ProductDetails
                .builder()
                .productName("Nike")
                .productId(1L)
                .build();

        OrderResponse.PaymentDetails paymentDetails = OrderResponse.PaymentDetails
                .builder()
                .paymentId(10L)
                .paymentStatus("SUCCESS")
                .paymentDate(Instant.now())
                .build();

        OrderResponse orderResponse = OrderResponse
                .builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .build();

        log.info(ORDER_CONFIRMATION_MESSAGE + " {}", orderResponse.toString());

        return orderResponse;
    }
}
