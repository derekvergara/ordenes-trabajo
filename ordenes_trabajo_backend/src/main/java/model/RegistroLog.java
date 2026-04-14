package model;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Document(collection = "registros_log")
public class RegistroLog {
    @Id
    private String id;

    @Field("usuario_id")
    private String usuarioid;

    @Field("nombre_usario")
    private String nombreusuario;

    @Field("orden_id")
    private String ordenid;  // nullable en caso si no es relacionado a la orden

    @Field("accion")
    private String accion; //crear,actualizar, desactivar,login,cerrar sesion

    @Field("entidad")
    private String entidad;// Usuario , orden de trabajo

    @Field("cambios")
    private Map<String, Object> cambios ;  // campos modificados con valores antiguos o nuevos

    @Field("direccion_ip")
    private String direccionip;

    @Field("user_agent")
    private String useragent;

    @Field("fecha_registro")
    private LocalDateTime fecharegistro = LocalDateTime.now();
}
