package com.proyeto.hand_craft_verse.controladores.usuarios;

import com.proyeto.hand_craft_verse.aplicacion.AplicacionUsuario;
import com.proyeto.hand_craft_verse.aplicacion.IAplicacion;
import com.proyeto.hand_craft_verse.dominio.productos.Producto;
import com.proyeto.hand_craft_verse.dominio.usuarios.UserRoles;
import com.proyeto.hand_craft_verse.dominio.usuarios.Usuario;
import com.proyeto.hand_craft_verse.dominio.usuarios.Vendedor;
import com.proyeto.hand_craft_verse.dto.LoginDTO;
import com.proyeto.hand_craft_verse.dto.UserGetDto;
import com.proyeto.hand_craft_verse.dto.UserRegisterDto;
import com.proyeto.hand_craft_verse.dto.UsuarioDTO;
import com.proyeto.hand_craft_verse.dto.Converter.UserDtoConverter;
import com.proyeto.hand_craft_verse.security.jwt.JwtTokenProvider;
import com.proyeto.hand_craft_verse.dto.Productos.ProductoDTO;
import com.proyeto.hand_craft_verse.dto.Converter.ProductoDtoConverter;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private IAplicacion<Producto> aplicacionProducto;

    @Autowired
    private IAplicacion<Vendedor> aplicacionVendedor;

    @Autowired
    private UserDtoConverter userDtoConverter;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
     private PasswordEncoder passwordEncoder;

    

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

    @PutMapping("/update/{id}")
@PreAuthorize("isAuthenticated()")
public ResponseEntity<Void> update(@PathVariable int id, @RequestBody UsuarioDTO entity, HttpServletResponse response) {
    Usuario usuario = aplicacionUsuario.buscar(id); // Se asigna el usuario tipo Usuario

    // Ahora, puedes proceder a actualizar los atributos del objeto `usuario`
    usuario.setNombre(entity.getNombre());
    usuario.setApellidos(entity.getApellidos());
    usuario.setPassword(passwordEncoder.encode(entity.getPassword()));
    usuario.setUsername(entity.getUsername());  // Actualizamos el username
    usuario.setTelefono(entity.getTelefono());
    usuario.setEmail(entity.getEmail());

    if (aplicacionUsuario.actualizar(usuario)) {
        // Generamos un nuevo token JWT después de la actualización
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(usuario.getUsername(), entity.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();
        
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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

    @PostMapping("/{id}/addFavoritos/{productoId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> agregarProductoFavorito(@PathVariable int id, @PathVariable int productoId) {
        Usuario usuario = aplicacionUsuario.buscar(id);
        Producto producto = aplicacionProducto.buscar(productoId);
        if (usuario != null && producto != null) {
            usuario.getProductosFavoritos().add(producto);
            aplicacionUsuario.actualizar(usuario);
            return ResponseEntity.ok("Producto agregado a favoritos");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario o producto no encontrado");
        }
    }

    @DeleteMapping("/{id}/deleteFavoritos/{productoId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> eliminarProductoFavorito(@PathVariable int id, @PathVariable int productoId) {
        // Buscar usuario y producto
        Usuario usuario = aplicacionUsuario.buscar(id);
        Producto producto = aplicacionProducto.buscar(productoId);

        // Validar que usuario y producto existan
        if (usuario == null || producto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario o producto no encontrado");
        }

        // Buscar el producto manualmente en la lista de favoritos
        Producto productoFavorito = usuario.getProductosFavoritos().stream()
                .filter(p -> p.getId() == productoId) // Comparar por ID
                .findFirst()
                .orElse(null);

        if (productoFavorito == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El producto no está en los favoritos del usuario");
        }

        // Eliminar la relación en ambas direcciones
        usuario.getProductosFavoritos().remove(productoFavorito);
        producto.getUsuariosFavoritos().remove(usuario);

        // Actualizar el usuario
        aplicacionUsuario.actualizar(usuario);

        return ResponseEntity.ok("Producto eliminado de favoritos");
    }

    @GetMapping("/{id}/favoritos")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ProductoDTO>> obtenerProductosFavoritos(@PathVariable int id) {
        Usuario usuario = aplicacionUsuario.buscar(id);
        if (usuario != null) {
            List<ProductoDTO> productosFavoritos = usuario.getProductosFavoritos().stream()
                    .map(ProductoDtoConverter::fromProducto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(productosFavoritos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{usuarioId}/isFavorito/{productoId}")
    public ResponseEntity<Boolean> esProductoFavorito(
            @PathVariable int usuarioId,
            @PathVariable int productoId) {
        try {
            // Buscar el usuario por su ID
            Usuario usuario = aplicacionUsuario.buscar(usuarioId);

            // Verificar si el productoId está en la lista de productos favoritos
            boolean esFavorito = usuario.getProductosFavoritos().stream()
                    .anyMatch(producto -> producto.getId() == productoId);

            // Retornar true si está en favoritos, false si no lo está
            return ResponseEntity.ok(esFavorito);
        } catch (Exception e) {
            // Manejo de excepciones (por ejemplo, si el usuario no existe)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(false);
        }
    }

}
