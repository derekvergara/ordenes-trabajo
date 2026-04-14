package security;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@AllArgsConstructor

public class UsuarioDetalles implements UserDetails {
    private String id;
    private String nombreusuario;
    private String contrasena;
    private String nombrecompleto;
    private String rol;
    private Boolean activo;

    public UsuarioDetalles(Usuario usuario){
        this.id =usuario.getId();
        this.nombreusuario = usuario.getNombreusuario();
        this.contrasena = usuario.getContrasena();
        this.nombrecompleto = usuario.getNombrecompleto();
        this.rol = usuario.getRol();
        this.activo = usuario.getActivo();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+ rol));
    }

    @Override
    public String getPassword(){
        return contrasena;
    }

    @Override
    public String getUsername(){
        return nombreusuario;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }
}
