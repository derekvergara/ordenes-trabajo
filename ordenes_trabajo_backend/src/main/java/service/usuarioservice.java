package service;

import dto.registrorequest;
import dto.UsuarioUpdateRequest;
import model.usuario;
import repository.usuariorepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class usuarioservice {
    private final usuariorepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final registrologservice registroLogService;

    public usuario registrarUsuario(registrorequest request, String usuarioActual) {
        if (usuarioRepository.existsByNombreUsuario(request.getNombreusuario())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        usuario usuario = new usuario();
        usuario.setNombreusuario(request.getNombreusuario());
        usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        usuario.setCorreo(request.getCorreo());
        usuario.setNombrecompleto(request.getNombrecompleto());
        usuario.setRol(request.getRol());
        usuario.setActivo(true);
        usuario.setFechacreacion(LocalDateTime.now());
        usuario.setFechaactualizacion(LocalDateTime.now());

        usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Registrar log
        registroLogService.registrarLog(
                usuarioActual,
                usuarioGuardado.getId(),
                "CREAR",
                "USUARIO",
                null,
                usuarioGuardado
        );

        return usuarioGuardado;
    }

    public List<usuario> obtenerTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public List<usuario> obtenerUsuariosActivos() {
        return usuarioRepository.findByActivo(true);
    }

    public Optional<usuario> obtenerUsuarioPorId(String id) {
        return usuarioRepository.findById(id);
    }

    public Usuario actualizarUsuario(String id, usuarioupdaterequest request, String usuarioActual) {
        usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Guardar valores antiguos para el log
        usuario usuarioAntiguo = new usuario();
        usuarioAntiguo.setNombrecompleto(usuario.getNombrecompleto());
        usuarioAntiguo.setRol(usuario.getRol());
        usuarioAntiguo.setActivo(usuario.getActivo());

        if (request.getNombrecompleto() != null) {
            usuario.setNombrecompleto(request.getNombrecompleto());
        }

        if (request.getRol() != null) {
            usuario.setRol(request.getRol());
        }

        if (request.getActivo() != null) {
            usuario.setActivo(request.getActivo());
        }

        usuario.setFechaActualizacion(LocalDateTime.now());
        usuario usuarioActualizado = usuarioRepository.save(usuario);

        // Registrar log
        registroLogService.registrarLog(
                usuarioActual,
                usuarioActualizado.getId(),
                "ACTUALIZAR",
                "USUARIO",
                usuarioAntiguo,
                usuarioActualizado
        );

        return usuarioActualizado;
    }

    public void desactivarUsuario(String id, String usuarioActual) {
        usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setActivo(false);
        usuario.setFechaactualizacion(LocalDateTime.now());
        usuarioRepository.save(usuario);

        // Registrar log
        registroLogService.registrarLog(
                usuarioActual,
                usuario.getId(),
                "DESACTIVAR",
                "USUARIO",
                null,
                usuario
        );
    }
}
