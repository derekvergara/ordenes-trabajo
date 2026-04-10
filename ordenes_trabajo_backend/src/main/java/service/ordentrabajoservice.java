package service;

import dto.filtroordenrequest;
import dto.ordentrabajorequest;
import model.ordentrabajo;
import repository.ordentrabajorepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class ordentrabajoservice {

    private final ordentrabajorepository ordenTrabajoRepository;
    private final registrologservice registroLogService;

    public ordentrabajo crearOrden(ordentrabajorequest request, String usuarioActual) {
        ordentrabajo orden = new ordentrabajo();

        // Generar número de orden único
        String numeroOrden = "OT-" + LocalDateTime.now().getYear() +
                String.format("%06d", UUID.randomUUID().hashCode() & Integer.MAX_VALUE);

        orden.setNumeroorden(numeroOrden);
        orden.setCliente(request.getCliente());
        orden.setTelefono(request.getTelefono());
        orden.setEquipo(request.getEquipo());
        orden.setDanio(request.getDanio());
        orden.setCosto(request.getCosto());
        orden.setEstado(request.getEstado());
        orden.setRepuestos(request.getRepuestos());
        orden.setObservaciones(request.getObservaciones());
        orden.setFechaingreso(request.getFechaingreso());
        orden.setFechasalida(request.getFechasalida());
        orden.setTecnicoasignado(request.getTecnicoasignado());
        orden.setActivo(true);
        orden.setFechacreacion(LocalDateTime.now());
        orden.setFechaactualizacion(LocalDateTime.now());
        orden.setCreadopor(usuarioActual);

        ordentrabajo ordenGuardada = ordenTrabajoRepository.save(orden);

        // Registrar log
        registroLogService.registrarLog(
                usuarioActual,
                ordenGuardada.getId(),
                "CREAR",
                "ORDEN_TRABAJO",
                null,
                ordenGuardada
        );

        return ordenGuardada;
    }

    public List<ordentrabajo> obtenerTodasOrdenes() {
        return ordenTrabajoRepository.findAll();
    }

    public List<ordentrabajo> obtenerOrdenesActivas() {
        return ordenTrabajoRepository.findByActivo(true);
    }

    public Optional<ordentrabajo> obtenerOrdenPorId(String id) {
        return ordenTrabajoRepository.findById(id);
    }

    public ordentrabajo actualizarOrden(String id, ordentrabajorequest request, String usuarioActual) {
        ordentrabajo orden = ordenTrabajoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada"));

        // Guardar valores antiguos para el log
        ordentrabajo ordenAntigua = new ordentrabajo();
        ordenAntigua.setCliente(orden.getCliente());
        ordenAntigua.setEquipo(orden.getEquipo());
        ordenAntigua.setDanio(orden.getDanio());
        ordenAntigua.setCosto(orden.getCosto());
        ordenAntigua.setEstado(orden.getEstado());

        orden.setCliente(request.getCliente());
        orden.setTelefono(request.getTelefono());
        orden.setEquipo(request.getEquipo());
        orden.setDanio(request.getDanio());
        orden.setCosto(request.getCosto());
        orden.setEstado(request.getEstado());
        orden.setRepuestos(request.getRepuestos());
        orden.setObservaciones(request.getObservaciones());
        orden.setFechaingreso(request.getFechaingreso());
        orden.setFechasalida(request.getFechasalida());
        orden.setTecnicoasignado(request.getTecnicoasignado());
        orden.setFechaactualizacion(LocalDateTime.now());

        ordentrabajo ordenActualizada = ordenTrabajoRepository.save(orden);

        // Registrar log
        registroLogService.registrarLog(
                usuarioActual,
                ordenActualizada.getId(),
                "ACTUALIZAR",
                "ORDEN_TRABAJO",
                ordenAntigua,
                ordenActualizada
        );

        return ordenActualizada;
    }

    public void desactivarOrden(String id, String usuarioActual) {
        ordentrabajo orden = ordenTrabajoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada"));

        orden.setActivo(false);
        orden.setFechaactualizacion(LocalDateTime.now());
        ordenTrabajoRepository.save(orden);

        // Registrar log
        registroLogService.registrarLog(
                usuarioActual,
                orden.getId(),
                "DESACTIVAR",
                "ORDEN_TRABAJO",
                null,
                orden
        );
    }

    public List<ordentrabajo> buscarPorFiltros(filtroordenrequest filtro) {
        return ordenTrabajoRepository.buscarPorFiltros(
                filtro.getActivo(),
                filtro.getCliente() != null ? filtro.getCliente() : "",
                filtro.getEquipo() != null ? filtro.getEquipo() : "",
                filtro.getEstado(),
                filtro.getTecnicoasigando()
        );
    }
}
