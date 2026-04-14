package repository;

import model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository

public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByNombreUsuario(String nombreusuario);
    Optional<Usuario> findByCorreo(String correo);
    List<Usuario> findByActivo(Boolean activo);
    List<Usuario> findByRol(String rol);
    boolean existsByNombreUsuario(String nombreusuario);
    boolean existsByCorreo(String correo);
}
