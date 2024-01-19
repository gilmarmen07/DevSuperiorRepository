package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemDTO {
    private long productId;
    private String name;
    private Double price;
    private Integer quantity;

    public OrderItemDTO(OrderItem orderItem) {
        productId = orderItem.getProduct().getId();
        name = orderItem.getProduct().getName();
        price = orderItem.getPrice();
        quantity = orderItem.getQuantity();
    }

    public Double getSubTotal(){
        return price * quantity;
    }
}
