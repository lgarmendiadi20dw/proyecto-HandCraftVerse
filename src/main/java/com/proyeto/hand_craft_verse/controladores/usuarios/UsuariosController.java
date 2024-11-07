package com.proyeto.hand_craft_verse.controladores.usuarios;

import com.proyeto.hand_craft_verse.aplicacion.AplicacionUsuario;
import com.proyeto.hand_craft_verse.dominio.usuarios.Usuario;
import com.proyeto.hand_craft_verse.dto.UserGetDto;
import com.proyeto.hand_craft_verse.dto.UserRegisterDto;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
@EnableMethodSecurity
@RequestMapping("/member")
public class UsuariosController {
    // @Autowired
    // @Qualifier("getAplicacionUsuarios")
    // private IAplicacion<Usuario> iaplicacionUsuario;

    @Autowired
    // @Qualifier("getAplicacionUsuario")
    private AplicacionUsuario aplicacionUsuario;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/registrar")
    public ResponseEntity<UserGetDto> registrar(@RequestBody UserRegisterDto user) {
        UserGetDto toReturn = aplicacionUsuario.guardar(user);
        if (toReturn != null) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(toReturn);

        } else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRegisterDto user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("Login successful");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/{id}")
    public Usuario viewMyPorfile(@PathVariable int id) {
        return aplicacionUsuario.buscar(id);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public String getMethodName(@AuthenticationPrincipal Usuario usuario) {
        String toReturn= usuario.getUsername() + " con email: " + usuario.getEmail();
        return toReturn;
    }

    @GetMapping("/me2")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String getMethodName2() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "hola " + usuario.getUsername();
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUsuarioByid(@PathVariable int id) {
        if (aplicacionUsuario.eliminar(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario Usuario) {

        try {

            if (aplicacionUsuario.guardar(Usuario)) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Usuario);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null);
            }

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);

        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> putMethodName(@PathVariable int id, @RequestBody Usuario entity) {

        Usuario Usuario = aplicacionUsuario.buscar(id);
        Usuario.setNombre(entity.getNombre());
        Usuario.setApellidos(entity.getApellidos());
        Usuario.setPassword(entity.getPassword());
        Usuario.setUsername(entity.getUsername());
        Usuario.setTelefono(entity.getTelefono());
        Usuario.setEmail(entity.getEmail());

        if (aplicacionUsuario.actualizar(entity) != null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @GetMapping("/all")
    public List<Map<String, Object>> verUsuariosList() {
        return aplicacionUsuario.obtenerDatosColumnas("id", "username")
                .stream()
                .map(fila -> Map.of("id", fila[0], "username", fila[1]))
                .toList();
    }

    @GetMapping("/allData")
    public List<Usuario> verUsuariosListAll() {
        return aplicacionUsuario.obtenerTodos();
    }

}
