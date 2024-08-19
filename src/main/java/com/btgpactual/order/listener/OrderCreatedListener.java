package com.btgpactual.order.listener;

import com.btgpactual.order.listener.dto.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

    @RabbitListener(queues = "order-queue")
    public void lister(Message<OrderCreatedEvent> message) {
        logger.info("Message consumed: {} " + message);
    }
}
