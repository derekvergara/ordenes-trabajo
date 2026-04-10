package repository;

import model.ordentrabajo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ordentrabajorepository extends MongoRepository<ordentrabajo, String> {
    List<ordentrabajo>findByActivo(Boolean activo);
    List<ordentrabajo>findByEstado(String estado);
    List<ordentrabajo> findByClienteContainingIgnoreCase(String cliente);
    List<ordentrabajo> findByEquipoContainingIgnoreCase(String equipo);
    List<ordentrabajo> findByTecnicoAsignado(String tecnicoAsignado);
    List<ordentrabajo> findByFechaIngresoBetween(LocalDate inicio, LocalDate fin);
    List<ordentrabajo> findByFechaSalidaBetween(LocalDate inicio, LocalDate fin);

    // personalizacion para filtros combinados
    @Query("{'$and': [" +
            "{'activo': ?0}," +
            "{'$or': [" +
            "  {'cliente': {$regex: ?1, $options: 'i'}}," +
            "  {'equipo': {$regex: ?2, $options: 'i'}}," +
            "  {'estado': ?3}," +
            "  {'tecnicoAsignado': ?4}" +
            "]}]}")
    List<ordentrabajo> buscarPorFiltros(Boolean activo, String cliente, String equipo,String estado, String tecnicoasignado);
}
