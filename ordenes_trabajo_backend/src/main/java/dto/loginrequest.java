package dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class loginrequest {
    @NotBlank(message = "El nombre de usuario es requerido")
    private String nombreusuario;

    @NotBlank(message = "La contraseña es requerida")
    private String contrasena;
}
