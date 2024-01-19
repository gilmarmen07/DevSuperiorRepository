package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;


import java.time.Instant;

@Getter
@AllArgsConstructor
public class PaymentDTO {
    private Long id;
    private Instant moment;

    public PaymentDTO(Payment payment) {
        id = payment.getId();
        moment = payment.getMoment();
    }
}
