package com.devsuperior.dscommerce.dto.errors;

import com.devsuperior.dscommerce.dto.FieldMessage;
import com.devsuperior.dscommerce.dto.errors.CustomError;
import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends CustomError {
    private final List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public void addErrors(String fieldName, String message) {
        errors.add(new FieldMessage(fieldName, message));
    }

    public void addAll(List<FieldMessage> errors) {
        this.errors.addAll(errors);
    }
}
