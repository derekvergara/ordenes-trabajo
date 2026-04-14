package service;

import model.RegistroLog;
import repository.RegistroLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class RegistroLogService {

    private final RegistroLogRepository registroLogRepository;
    private final HttpServletRequest request;

    public void registrarLog(String usuarioId, String nombreUsuario, String ordenId,
                             String accion, String entidad, Map<String, Object> cambios) {
        RegistroLog log = new RegistroLog();
        log.setUsuarioid(usuarioId);
        log.setNombreusuario(nombreUsuario);
        log.setOrdenid(ordenId);
        log.setAccion(accion);
        log.setEntidad(entidad);
        log.setCambios(cambios);
        log.setDireccionip(getClientIp());
        log.setUseragent(request.getHeader("User-Agent"));

        registroLogRepository.save(log);
    }

    public void registrarLog(String usuarioActual, String entidadId, String accion,
                             String entidad, Object objetoAntiguo, Object objetoNuevo) {
        Map<String, Object> cambios = new HashMap<>();

        if (objetoAntiguo != null && objetoNuevo != null) {
            // aqui podrías implementar una comparacion mas sofisticada
            cambios.put("objeto_antiguo", objetoAntiguo);
            cambios.put("objeto_nuevo", objetoNuevo);
        }

        // para simplicidad, usamos el mismo nombre de Usuario
        registrarLog(usuarioActual, usuarioActual, entidadId, accion, entidad, cambios);
    }

    private String getClientIp() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    public List<RegistroLog> obtenerLogsPorUsuario(String usuarioId) {
        return registroLogRepository.findByUsuarioId(usuarioId);
    }

    public List<RegistroLog> obtenerLogsPorOrden(String ordenId) {
        return registroLogRepository.findByOrdenId(ordenId);
    }

    public List<RegistroLog> obtenerTodosLogs() {
        return registroLogRepository.findAll();
    }
}
