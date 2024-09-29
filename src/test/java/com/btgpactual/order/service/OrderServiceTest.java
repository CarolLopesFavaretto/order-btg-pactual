package com.btgpactual.order.service;

import com.btgpactual.order.entity.Order;
import com.btgpactual.order.entity.OrderItem;
import com.btgpactual.order.listener.dto.OrderCreatedEvent;
import com.btgpactual.order.listener.dto.OrderItemEvent;
import com.btgpactual.order.repository.OrderRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private OrderService orderService;

    @Nested
    class CreateOrder {

        @Test
        void shouldSaveOrder() {
            // Arrange
            OrderCreatedEvent event = new OrderCreatedEvent(
                    123L,
                    456L,
                    List.of(new OrderItemEvent("product", 2, BigDecimal.TEN))
            );
            Order order = new Order();
            order.setId(123L);
            order.setCustomerId(456L);
            order.setItems(List.of(new OrderItem("product", 2, BigDecimal.TEN)));
            order.setAmount(BigDecimal.valueOf(20));

            // Act
            orderService.saveOrder(event);

            // Assert
            verify(orderRepository, times(1)).save(any(Order.class));
        }
    }

}