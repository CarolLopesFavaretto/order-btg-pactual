package com.btgpactual.order.service;

import com.btgpactual.order.Entity.Order;
import com.btgpactual.order.Entity.OrderItem;
import com.btgpactual.order.listener.dto.OrderCreatedEvent;
import com.btgpactual.order.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);


    @Autowired
    private OrderRepository orderRepository;


    public void saveOrder(OrderCreatedEvent event) {
        var entity = new Order();

        entity.setId(event.productCode());
        entity.setCustomerId(event.clientCode());
        entity.setItems(getOrderItems(event));
        entity.setAmount(getTotal(event));

        orderRepository.save(entity);
        logger.info("Order saved: {} " + entity);
    }

    private BigDecimal getTotal(OrderCreatedEvent event) {
        return event.items()
                .stream()
                .map(i -> i.price().multiply(BigDecimal.valueOf(i.quantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private static List<OrderItem> getOrderItems(OrderCreatedEvent event) {
        return event.items().stream()
                .map(i -> new OrderItem(i.product(), i.quantity(), i.price()))
                .toList();
    }
}
