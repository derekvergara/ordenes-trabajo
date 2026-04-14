package dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioRequest {
    @NotBlank(message ="El nombre de Usuario es requerido")
    @Size(min = 3, max= 50, message = "El mensaje del Usuario debe teener entre 3 a 50 caracteres")
    private String nombreusuario;

    @NotBlank(message = "la contraseña es requerida")
    @Size(min = 6 , message ="Lacontraseña debe tener al menos 6 caracteres")
    private String contrasena;

    @NotBlank(message = "El correo electronico es requerido")
    @Size(message = "El correo electronco tiene que ser valido")
    private String correo;

    @NotBlank(message = "EL nombre completo es requerido")
    private String nombrecompleto;

    private String rol= "TECNICO";
}
