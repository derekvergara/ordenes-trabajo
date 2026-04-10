package repository;

import model.registrolog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository

public interface registrologrepository extends MongoRepository<registrolog, String>{

    List<registrolog> findByUsuarioId (String usuarioid);
    List<registrolog> findByordenId ( String ordenid);
    List<registrolog> findByEntidad (String entidad);
    List<registrolog> findByFechaRegistroBetween (LocalDateTime inicio, LocalDateTime fin);
    List<registrolog> findByAccion (String accion);

}
