package com.devsuperior.services;

import com.devsuperior.entities.Order;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {
    public double shipment(Order order) {
        if(order.getBasic() < 100.00){
            return 20;
        } else if(order.getBasic() >= 100.00 && order.getBasic() < 200.00){
            return 12;
        } else if(order.getBasic() >= 200.00){
            return 0;
        }
        return 0;
    }
}
