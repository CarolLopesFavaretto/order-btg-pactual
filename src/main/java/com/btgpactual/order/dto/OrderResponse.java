package com.btgpactual.order.dto;

import com.btgpactual.order.entity.Order;

import java.math.BigDecimal;

public record OrderResponse(Long id, Long customerId, BigDecimal amount) {

    public static OrderResponse fromEntity(Order order) {
        return new OrderResponse(order.getId(), order.getCustomerId(), order.getAmount());
    }
}
