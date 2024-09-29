package com.btgpactual.order.listener;

import com.btgpactual.order.factory.OrderPayloadFactory;
import com.btgpactual.order.service.OrderService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderCreatedListenerTest {

    @Mock
    private OrderService service;

    @InjectMocks
    private OrderCreatedListener listener;

    @Nested
    class Lister {

        @Test
        void shouldCallServiceWithCorrectParameters() {
//            ARRANGE
            var event = OrderPayloadFactory.createOrderCreatedEvent();
            var message = org.springframework.messaging.support.MessageBuilder.withPayload(event).build();
//            ACT
            service.saveOrder(message.getPayload());
//            ASSERT
            verify(service).saveOrder(event);
            verify(service, times(1)).saveOrder(eq(message.getPayload()));
        }
    }
}
