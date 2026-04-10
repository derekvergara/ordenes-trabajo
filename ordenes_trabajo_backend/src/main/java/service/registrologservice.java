package service;

import model.registrolog;
import repository.registrologrepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class registrologservice {

    private final registrologrepository registroLogRepository;
    private final HttpServletRequest request;

    public void registrarLog(String usuarioId, String nombreUsuario, String ordenId,
                             String accion, String entidad, Map<String, Object> cambios) {
        registrolog log = new registrolog();
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
            // Aquí podrías implementar una comparación más sofisticada
            cambios.put("objeto_antiguo", objetoAntiguo);
            cambios.put("objeto_nuevo", objetoNuevo);
        }

        // Para simplicidad, usamos el mismo nombre de usuario
        registrarLog(usuarioActual, usuarioActual, entidadId, accion, entidad, cambios);
    }

    private String getClientIp() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    public List<registrolog> obtenerLogsPorUsuario(String usuarioId) {
        return registroLogRepository.findByUsuarioId(usuarioId);
    }

    public List<registrolog> obtenerLogsPorOrden(String ordenId) {
        return registroLogRepository.findByOrdenId(ordenId);
    }

    public List<registrolog> obtenerTodosLogs() {
        return registroLogRepository.findAll();
    }
}
