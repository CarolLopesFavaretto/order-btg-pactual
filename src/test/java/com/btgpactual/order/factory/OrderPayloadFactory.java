package com.btgpactual.order.factory;

import com.btgpactual.order.listener.dto.OrderCreatedEvent;
import com.btgpactual.order.listener.dto.OrderItemEvent;

import java.math.BigDecimal;
import java.util.List;

public class OrderPayloadFactory {

    public static OrderCreatedEvent createOrderCreatedEvent() {
        var orderItemEvent = new OrderItemEvent("product",1,BigDecimal.ONE);
        return new OrderCreatedEvent(1L, 1L, List.of(orderItemEvent));
    }
}
