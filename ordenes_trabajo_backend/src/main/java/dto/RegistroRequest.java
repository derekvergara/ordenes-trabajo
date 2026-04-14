package dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistroRequest {

    @NotBlank(message = "El nombre de Usuario es requerido")
    @Size(min = 3, max = 20, message = "El nombre de Usuario debe tener entre 3 a 20 caracteres")
    private String nombreusuario;

    @NotBlank(message = "La contraseña es requerida")
    @Size(min=6, max= 50, message = "La contraseña debe tener al menos 6 caracteres")
    private String contrasena;

    @NotBlank(message = "El correo es requerido")
    @Email(message = "El correo debe ser valido")
    private String correo;

    @NotBlank(message = "El nombre completo es requerido")
    private String nombrecompleto;

    private String rol = "TECNICO"; // nombre por defecto tecnico

}
