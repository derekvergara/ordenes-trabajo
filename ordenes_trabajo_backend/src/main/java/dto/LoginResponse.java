package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class LoginResponse {

    private String token;
    private String tipo="Bearer";
    private String id;
    private String nombreusuario;
    private String nombrecompleto;
    private String rol;

}
