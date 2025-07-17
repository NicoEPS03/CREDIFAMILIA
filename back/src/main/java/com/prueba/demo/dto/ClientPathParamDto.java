package com.prueba.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record ClientPathParamDto(
        @NotBlank(message = "Documento requerido")
        String document
) {
    public String getDocument() {
        return this.document;
    }
}
