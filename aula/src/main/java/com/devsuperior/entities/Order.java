package com.devsuperior.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    private String code;
    private Double basic;
    private Double discount;

    public Order(String code, Double basic, Double discount) {
        this.code = code;
        this.basic = basic;
        this. discount = discount;
    }
}
