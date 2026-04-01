package dto;

package com.ordenestrabajo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data

public class ordentrabajorequest {
    @NotBlank(message = "El cliente es requerido")
    private String cliente;

    @NotBlank(message = "El equipo es requerido")
    private String equipo;

    @NotBlank(message = "El daño es requerido")
    private String danio;

    @NotNull(message = "El costo es requerido")
    private Double costo;

    @NotBlank(message = "El estado es requerido")
    private String estado;

    private String repuestos;

    private String observaciones;

    private LocalDateTime fechaingreso;

    private LocalDateTime fechasalida;

    private String tecnicoasignado;
}
