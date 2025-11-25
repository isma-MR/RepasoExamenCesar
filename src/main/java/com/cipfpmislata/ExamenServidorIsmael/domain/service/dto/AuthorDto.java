package com.cipfpmislata.ExamenServidorIsmael.domain.service.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthorDto(
        Long id,
        @NotBlank(message = "Nombre no puede ser nulo o vacío")
        String name,
        String slug
) {
}
