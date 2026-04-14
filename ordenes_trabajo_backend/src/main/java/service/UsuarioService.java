package service;

import dto.RegistroRequest;
import dto.UsuarioUpdateRequest;
import model.Usuario;
import repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegistroLogService registroLogService;

    public Usuario registrarUsuario(RegistroRequest request, String usuarioActual) {
        if (usuarioRepository.existsByNombreUsuario(request.getNombreusuario())) {
            throw new RuntimeException("El nombre de Usuario ya existe");
        }

        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombreusuario(request.getNombreusuario());
        usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        usuario.setCorreo(request.getCorreo());
        usuario.setNombrecompleto(request.getNombrecompleto());
        usuario.setRol(request.getRol());
        usuario.setActivo(true);
        usuario.setFechacreacion(LocalDateTime.now());
        usuario.setFechaactualizacion(LocalDateTime.now());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

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

    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> obtenerUsuariosActivos() {
        return usuarioRepository.findByActivo(true);
    }

    public Optional<Usuario> obtenerUsuarioPorId(String id) {
        return usuarioRepository.findById(id);
    }

    public Usuario actualizarUsuario(String id, usuarioupdaterequest request, String usuarioActual) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Guardar valores antiguos para el log
        Usuario usuarioAntiguo = new Usuario();
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
        Usuario usuarioActualizado = usuarioRepository.save(usuario);

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
        Usuario usuario = usuarioRepository.findById(id)
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
