package com.btgpactual.order.service;

import com.btgpactual.order.dto.OrderResponse;
import com.btgpactual.order.entity.Order;
import com.btgpactual.order.entity.OrderItem;
import com.btgpactual.order.listener.dto.OrderCreatedEvent;
import com.btgpactual.order.repository.OrderRepository;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    MongoTemplate template;


    public void saveOrder(OrderCreatedEvent event) {
        var entity = new Order();

        entity.setId(event.productCode());
        entity.setCustomerId(event.clientCode());
        entity.setItems(getOrderItems(event));
        entity.setAmount(getTotal(event));

        orderRepository.save(entity);
        logger.info("Order saved: {} " + entity);
    }

    public Page<OrderResponse> findAllByCustomerId(Long customerId, PageRequest pageRequest) {
        var orders =  orderRepository.findAllByCustomerId(customerId, pageRequest);
        return orders.map(OrderResponse::fromEntity);
    }

    public BigDecimal findAmountOnOrdersByCustomerId(Long customerId) {
        var aggregation = newAggregation(
                match(Criteria.where("customerId").is(customerId)),
                group().sum("amount").as("amount")
        );
        var response = template.aggregate(aggregation,"order", Document.class);

        return new BigDecimal(response.getUniqueMappedResult().get("amount").toString());
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
