package com.btgpactual.order.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    private String product;
    private Integer quantity;
    private BigDecimal price;
}
