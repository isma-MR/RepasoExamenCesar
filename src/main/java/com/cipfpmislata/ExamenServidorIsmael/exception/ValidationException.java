package com.cipfpmislata.ExamenServidorIsmael.exception;

import java.util.Set;
import java.util.stream.Collectors;
import jakarta.validation.ConstraintViolation;

public class ValidationException extends RuntimeException {

    private final Set<ConstraintViolation<?>> violations;

    public ValidationException(String message) {
        super(message);
        this.violations = Set.of();
    }

    public ValidationException(Set<? extends ConstraintViolation<?>> violations) {
        super("Errores de validación detectados: " + violations.size());
        this.violations = Set.copyOf(violations);
    }

    public Set<ConstraintViolation<?>> getViolations() {
        return violations;
    }

    @Override
    public String getMessage() {
        return violations.stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining(", "));
    }
}
