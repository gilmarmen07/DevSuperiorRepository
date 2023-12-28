package com.devsuperior.services;

import com.devsuperior.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private ShippingService shippingService;

    public double total(Order order) {
        return order.getBasic() - getDiscount(order) + shippingService.shipment(order);
    }

    private double getDiscount(Order order){
        return (order.getBasic() * order.getDiscount())/100;
    }
}
