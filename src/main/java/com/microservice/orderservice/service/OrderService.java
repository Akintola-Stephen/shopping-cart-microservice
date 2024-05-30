package com.microservice.orderservice.service;

import com.microservice.orderservice.dto.request.OrderRequest;
import com.microservice.orderservice.dto.response.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
    OrderResponse getOrderDetails(long orderId);
}
