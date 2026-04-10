package repository;

import model.usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository

public interface usuariorepository extends MongoRepository<usuario, String> {

    Optional<usuario> findByNombreUsuario(String nombreusuario);
    Optional<usuario> findByCorreo(String correo);
    List<usuario> findByActivo(Boolean activo);
    List<usuario> findByRol(String rol);
    boolean existsByNombreUsuario(String nombreusuario);
    boolean existsByCorreo(String correo);
}
