package com.proyeto.hand_craft_verse.controladores.usuarios;

import com.proyeto.hand_craft_verse.aplicacion.AplicacionUsuario;
import com.proyeto.hand_craft_verse.dominio.usuarios.Usuario;
import com.proyeto.hand_craft_verse.dto.LoginDTO;
import com.proyeto.hand_craft_verse.dto.UserGetDto;
import com.proyeto.hand_craft_verse.dto.UserRegisterDto;
import com.proyeto.hand_craft_verse.dto.UsuarioDTO;
import com.proyeto.hand_craft_verse.dto.Converter.UserDtoConverter;
import com.proyeto.hand_craft_verse.security.jwt.JwtTokenProvider;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@AllArgsConstructor
@EnableMethodSecurity
@RequestMapping("/member")
public class UsuariosController {
    @Autowired
    private AplicacionUsuario aplicacionUsuario;

    @Autowired
    private UserDtoConverter userDtoConverter;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody UserRegisterDto user, HttpServletResponse response) {
        UserGetDto toReturn = aplicacionUsuario.guardar(user);
        if (toReturn != null) {
            // Llamar al método login
            LoginDTO loginDto = new LoginDTO();
            loginDto.setUsername(user.getUsername());
            loginDto.setPassword(user.getPassword());
            return login(loginDto, response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    @GetMapping("/{id}")
    public UsuarioDTO viewMyPorfile(@PathVariable int id) {
        UsuarioDTO toReturn = userDtoConverter.fromUsuarioToUsuarioDTO(aplicacionUsuario.buscar(id));
        return toReturn;
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public String getMethodName(@AuthenticationPrincipal Usuario usuario) {
        String toReturn = usuario.getUsername() + " con email: " + usuario.getEmail();
        return toReturn;
    }

    @GetMapping("/me2")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String getMethodName2() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "hola " + usuario.getUsername();
    }

    @GetMapping("/me3")
    @PreAuthorize("isAuthenticated()")
    public UsuarioDTO getMethodName3(@AuthenticationPrincipal Usuario usuario) {

        
        return userDtoConverter.fromUsuarioToUsuarioDTO(usuario);

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
   
    

    // @PutMapping("/update/{id}")
    // public ResponseEntity<Void> update(@PathVariable int id, @RequestBody UsuarioDTO entity) {

    //     Usuario usuario = aplicacionUsuario.buscar(id);
    //     usuario.setNombre(entity.getNombre());
    //     usuario.setApellidos(entity.getApellidos());
    //     usuario.setPassword(entity.getPassword());
    //     usuario.setUsername(entity.getUsername());
    //     usuario.setTelefono(entity.getTelefono());
    //     usuario.setEmail(entity.getEmail());

    //     if (aplicacionUsuario.actualizar(usuario)  {
    //         return ResponseEntity.status(HttpStatus.NO_CONTENT)
    //                 .body(null);
    //     } else {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND)
    //                 .body(null);
    //     }
    // }

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);

            ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
                    .path("/")
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None")
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            Usuario usuario = (Usuario) authentication.getPrincipal();
            UsuarioDTO usuarioDTO = userDtoConverter.fromUsuarioToUsuarioDTO(usuario);

            return ResponseEntity.ok(usuarioDTO);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletResponse response) {

        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok("Logged out");
    }

}
