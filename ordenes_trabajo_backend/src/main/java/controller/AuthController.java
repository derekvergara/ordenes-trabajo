package controller;


import dto.LoginRequest;
import dto.LoginResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.UsuarioService;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticacion", description = "Endpoints para autenticacion de usuarios")

public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesion", description = "autentica un Usuario y devuelve el token JWT")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getNombreusuario(),
                        loginRequest.getContrasena()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UsuarioDetalles usuarioDetalles = (UsuariodDetalles) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(usuarioDetalles);

        LoginResponse response = new LoginResponse(
                jwt,
                "Bearer",
                usuarioDetalles.getId(),
                usuarioDetalles.getUsername(),
                usuarioDetalles.getNombreCompleto(),
                usuarioDetalles.getRol()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/registro")
    @Operation(summary = "Registrar nuevo usuario", description = "Crea una nueva cuenta de usuario")
    public ResponseEntity<LoginResponse> registrar(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        // Primero creamos el usuario
        Usuario usuario = usuarioService.crearUsuario(usuarioRequest);

        // Luego autenticamos
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usuarioRequest.getNombreUsuario(),
                        usuarioRequest.getContrasena()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UsuarioDetalles usuarioDetalles = (UsuarioDetalles) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(usuarioDetalles);

        LoginResponse response = new LoginResponse(
                jwt,
                "Bearer",
                usuarioDetalles.getId(),
                usuarioDetalles.getUsername(),
                usuarioDetalles.getNombreCompleto(),
                usuarioDetalles.getRol()
        );

        return ResponseEntity.ok(response);
    }

}
