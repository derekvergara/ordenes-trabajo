package repository;

import model.OrdenTrabajo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrdenTrabajoRepository extends MongoRepository<OrdenTrabajo, String> {
    List<OrdenTrabajo>findByActivo(Boolean activo);
    List<OrdenTrabajo>findByEstado(String estado);
    List<OrdenTrabajo> findByClienteContainingIgnoreCase(String cliente);
    List<OrdenTrabajo> findByEquipoContainingIgnoreCase(String equipo);
    List<OrdenTrabajo> findByTecnicoAsignado(String tecnicoAsignado);
    List<OrdenTrabajo> findByFechaIngresoBetween(LocalDate inicio, LocalDate fin);
    List<OrdenTrabajo> findByFechaSalidaBetween(LocalDate inicio, LocalDate fin);

    // personalizacion para filtros combinados
    @Query("{'$and': [" +
            "{'activo': ?0}," +
            "{'$or': [" +
            "  {'cliente': {$regex: ?1, $options: 'i'}}," +
            "  {'equipo': {$regex: ?2, $options: 'i'}}," +
            "  {'estado': ?3}," +
            "  {'tecnicoAsignado': ?4}" +
            "]}]}")
    List<OrdenTrabajo> buscarPorFiltros(Boolean activo, String cliente, String equipo, String estado, String tecnicoasignado);
}
