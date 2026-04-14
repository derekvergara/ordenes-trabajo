package dto;

import lombok.Data;

@Data

public class OrdenTrabajoResponse {

    private String id;
    private String numeroorden;
    private String cliente;
    private String telefono;
    private String danio;
    private String costo;
    private String estado;
    private String repuestos;
    private String observaciones;
    private String fechaingreso;
    private String fechsalida;
    private String activo;
    private String fechacreacion;
    private String fechaactualizacion;

}
