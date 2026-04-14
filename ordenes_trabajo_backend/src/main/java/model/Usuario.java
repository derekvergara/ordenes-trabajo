package model;

import org.springframework.data.annotation.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "usuarios")
public class Usuario {
    @Id
    private String id;

    @Indexed(unique = true)
    @Field("nombre_usuario")
    private String nombreusuario;

    @Field("contrasena")
    private String contrasena;

    @Field("correo")
    private String correo;

    @Field("nombre_completo")
    private String nombrecompleto;

    @Field("rol")
    private String rol; //Administrador, tecnico

    @Field("activo")
    private Boolean activo = true;

    @Field("fecha_creacion")
    private LocalDateTime fechacreacion;

    @Field("fecha_actualizacion")
    private LocalDateTime fechaactualizacion;
}
