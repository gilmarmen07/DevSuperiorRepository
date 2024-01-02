package com.devsuperior.dscomerce.entities;

import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "tb_order_item")
public class OrderItem {

    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    @EmbeddedId
    private OrderItemPk id = new OrderItemPk();
    private Double price;
    private Integer quantity;

    public OrderItem(Order order, Product product, Double price, Integer quantity) {
        this.id.setOrder(order);
        this.id.setProduct(product);
        this.price = price;
        this.quantity = quantity;
    }

    public void setOrder(Order order) {
        this.id.setOrder(order);
    }

    public void setProduct(Product product) {
        this.id.setProduct(product);
    }

    public Order getOrder() {
        return this.id.getOrder();
    }

    public Product getProduct() {
        return this.id.getProduct();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItem orderItem = (OrderItem) o;

        return Objects.equals(id, orderItem.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}