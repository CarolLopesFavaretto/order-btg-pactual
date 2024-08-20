package com.btgpactual.order.factory;

import com.btgpactual.order.dto.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.List;

public class OrderResponseFactory {

    public static Page<OrderResponse> buildWithOneItem(){
        var orderResponse = new OrderResponse(1l, 2l, BigDecimal.valueOf(100.0));
        return new PageImpl<>(List.of(orderResponse));
    }

}
