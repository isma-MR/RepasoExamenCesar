package com.cipfpmislata.ExamenServidorIsmael.domain.service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.util.List;

public record BookDto(
        Long id,
        @NotNull(message = "ISBN es obligatorio")
        @Pattern(regexp = "\\d{13}", message = "ISBN debe tener 13 dígitos")
        String isbn,
        String titleEs,
        @NotNull(message = "El precio base no puede ser nulo")
        @DecimalMin(value = "0.0", inclusive = true, message = "El precio base debe ser mayor o igual a 0")
        BigDecimal price,
        PublisherDto publisher,
        List<AuthorDto> authors
) {
}
