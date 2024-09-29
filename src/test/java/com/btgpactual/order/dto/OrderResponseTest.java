package com.btgpactual.order.dto;

import com.btgpactual.order.factory.OrderEntityFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderResponseTest {

    @Nested
    class fromEntity {

        @Test
        void shouldReturnOrderResponse() {
//            ARRANGE
            var input = OrderEntityFactory.build();
//            ACT
            var output = OrderResponse.fromEntity(input);
//            ASSERT
            assertEquals(input.getId(), output.id());
            assertEquals(input.getCustomerId(), output.customerId());
            assertEquals(input.getAmount(), output.amount());
        }
    }
}