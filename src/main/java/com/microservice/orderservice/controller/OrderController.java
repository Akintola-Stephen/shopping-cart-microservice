package com.microservice.orderservice.controller;

import com.microservice.orderservice.dto.request.OrderRequest;
import com.microservice.orderservice.dto.response.OrderResponse;
import com.microservice.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Log4j2
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/place-order")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) {
        log.info("placeOrder");
        log.info("orderRequest: {}", orderRequest.toString());

        long orderId = orderService.placeOrder(orderRequest);
        log.info("orderId: {}", orderId);

        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId) {

        log.info("OrderController | getOrderDetails is called");

        OrderResponse orderResponse
                = orderService.getOrderDetails(orderId);

        log.info("OrderController | getOrderDetails | orderResponse : {}", orderResponse.toString());

        return new ResponseEntity<>(orderResponse,
                HttpStatus.OK);
    }
}
