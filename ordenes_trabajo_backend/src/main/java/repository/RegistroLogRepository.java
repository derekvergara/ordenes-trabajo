package repository;

import model.RegistroLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository

public interface RegistroLogRepository extends MongoRepository<RegistroLog, String>{

    List<RegistroLog> findByUsuarioId (String usuarioid);
    List<RegistroLog> findByOrdenId (String ordenid);
    List<RegistroLog> findByEntidad (String entidad);
    List<RegistroLog> findByFechaRegistroBetween (LocalDateTime inicio, LocalDateTime fin);
    List<RegistroLog> findByAccion (String accion);

}
