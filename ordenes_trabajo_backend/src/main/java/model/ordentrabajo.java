package model;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "ordenes_trabajo")
public class ordentrabajo {
    @Id
    private String id;

    @Field("numero_orden")
    private String numeroorden;

    @Field("cliente")
    private String cliente;

    @Field("telefono")
    private String telefono; // opcional

    @Field("equipo")
    private String equipo;

    @Field("danio")
    private String danio;

    @Field("costo")
    private Double costo;

    @Field ("estado")
    private String estado; // pendiente, en proceso, completado, cancelado

    @Field("repuestos")
    private String repuestos;

    @Field("observaciones")
    private String observaciones; //opcional

    @Field("fecha_ingreso")
    private LocalDateTime fechaingreso;

    @Field("fecha_salida")
    private LocalDateTime fechasalida;

    @Field("tecnico_asigando")
    private String tecnicoasignado;

    @Field("activo")
    private Boolean activo = true;

    @Field("fecha_creacion")
    private String fechacreacion;

    @Field("fecha_actualizacion")
    private LocalDateTime fechaactualizacion;

    @Field("creado_por")
    private String creadopor;

}
