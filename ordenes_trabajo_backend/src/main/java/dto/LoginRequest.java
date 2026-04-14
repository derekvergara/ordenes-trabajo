package dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "El nombre de Usuario es requerido")
    private String nombreusuario;

    @NotBlank(message = "La contraseña es requerida")
    private String contrasena;
}
