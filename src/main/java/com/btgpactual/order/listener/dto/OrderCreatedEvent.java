package com.btgpactual.order.listener.dto;

import java.util.List;

public record OrderCreatedEvent(Long productCode, Long clientCode, List<OrderItemEvent> items) {
}
