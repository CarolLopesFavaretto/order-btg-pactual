package com.btgpactual.order.factory;

import com.btgpactual.order.entity.Order;
import com.btgpactual.order.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public class OrderEntityFactory {

    public static Order build() {
        var items = List.of(OrderItem.builder()
                .quantity(1)
                .price(BigDecimal.valueOf(100.0))
                .build());

        return Order.builder()
                .id(1L)
                .customerId(1L)
                .amount(BigDecimal.valueOf(100.0))
                .items(items)
                .build();
    }
}
