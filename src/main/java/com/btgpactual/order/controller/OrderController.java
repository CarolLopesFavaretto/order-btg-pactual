package com.btgpactual.order.controller;

import com.btgpactual.order.dto.ApiResponse;
import com.btgpactual.order.dto.OrderResponse;
import com.btgpactual.order.dto.PaginationResponse;
import com.btgpactual.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("customer/{customerId}")
    public ResponseEntity<ApiResponse<OrderResponse>> listOrders (@PathVariable("customerId") Long customerId,
                                                                          @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                          @RequestParam(name = "pageSize", defaultValue = "10") Integer size) {

        var pageResponse = orderService.findAllByCustomerId(customerId, PageRequest.of(page, size));
        var amountOnOrders = orderService.findAmountOnOrdersByCustomerId(customerId);
        return ResponseEntity.ok(new ApiResponse<>(
                Map.of("amountOnOrders", amountOnOrders),
                pageResponse.getContent(),
                PaginationResponse.fromPage(pageResponse)));
    }
}
