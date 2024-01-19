package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.enuns.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderDTO {
    private Long id;
    private Instant moment;
    private OrderStatus status;
    private ClientDTO client;
    private PaymentDTO payment;

    @NotEmpty(message = "Must have at least one item")
    private List<OrderItemDTO> items = new ArrayList<>();

    public OrderDTO(Long id, Instant moment, OrderStatus status, ClientDTO client, PaymentDTO payment) {
        this.id = id;
        this.moment = moment;
        this.status = status;
        this.client = client;
        this.payment = payment;
    }

    public OrderDTO(Order order) {
        id = order.getId();
        moment = order.getMoment();
        status = order.getStatus();
        client = new ClientDTO(order.getClient());
        payment = order.getPayment() != null ? new PaymentDTO(order.getPayment()) : null;
        items = order.getItems().stream().map(OrderItemDTO::new).toList();
    }

    public Double getTotal() {
        return items.stream().mapToDouble(OrderItemDTO::getSubTotal).sum();
    }
}
