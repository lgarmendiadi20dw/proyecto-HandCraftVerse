package com.proyeto.hand_craft_verse.controladores.usuarios;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyeto.hand_craft_verse.aplicacion.AplicacionUsuario;
import com.proyeto.hand_craft_verse.aplicacion.IAplicacion;
import com.proyeto.hand_craft_verse.dominio.productos.Producto;
import com.proyeto.hand_craft_verse.dominio.usuarios.UserRoles;
import com.proyeto.hand_craft_verse.dominio.usuarios.Usuario;
import com.proyeto.hand_craft_verse.dominio.usuarios.Vendedor;
import com.proyeto.hand_craft_verse.dto.LoginDTO;
import com.proyeto.hand_craft_verse.dto.UserGetDto;
import com.proyeto.hand_craft_verse.dto.UserRegisterDto;
import com.proyeto.hand_craft_verse.dto.VendedorDTO;
import com.proyeto.hand_craft_verse.dto.Converter.DtoConverter;
import com.proyeto.hand_craft_verse.dto.Converter.ProductoDtoConverter;
import com.proyeto.hand_craft_verse.dto.Productos.ProductoDTO;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/member/seller")
public class VendedorController {
    @Autowired
    IAplicacion<Vendedor> aplicacionVendedor;

    @Autowired
    private AplicacionUsuario aplicacionUsuario;
    @Autowired
    private UsuariosController usuariosController;

    @GetMapping("/{id}")
    public Vendedor viewMyPorfile(@PathVariable int id) {
        return aplicacionVendedor.buscar(id);
    }

    @PostMapping("/createSeller")
    public ResponseEntity<?> createSellers(HttpServletResponse response) {
        // Lista de usuarios a registrar
        List<UserRegisterDto> users = List.of(
            UserRegisterDto.builder()
                .username("craftLover")
                .email("craftlover@example.com")
                .emailConfirm("craftlover@example.com")
                .password("SecurePass123!")
                .passwordConfirm("SecurePass123!")
                .build(),
            UserRegisterDto.builder()
                .username("artisanPro")
                .email("artisanpro@example.com")
                .emailConfirm("artisanpro@example.com")
                .password("ProCraftsman456!")
                .passwordConfirm("ProCraftsman456!")
                .build(),
            UserRegisterDto.builder()
                .username("handyArtist")
                .email("handyartist@example.com")
                .emailConfirm("handyartist@example.com")
                .password("Creative123!")
                .passwordConfirm("Creative123!")
                .build(),
            UserRegisterDto.builder()
                .username("uniqueCrafter")
                .email("uniquecrafter@example.com")
                .emailConfirm("uniquecrafter@example.com")
                .password("UniquePass789!")
                .passwordConfirm("UniquePass789!")
                .build(),
            UserRegisterDto.builder()
                .username("artCrafting")
                .email("artcrafting@example.com")
                .emailConfirm("artcrafting@example.com")
                .password("ArtisticPass123!")
                .passwordConfirm("ArtisticPass123!")
                .build()
        );
    
        // Registrar cada usuario
        for (UserRegisterDto user : users) {
            ResponseEntity<?> responseEntity = registrar(user, response);
            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error registering user: " + user.getUsername());
            }
        }
    
        return ResponseEntity.ok("All users registered successfully");
    }
    

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody UserRegisterDto user, HttpServletResponse response) {
        UserGetDto toReturn = aplicacionUsuario.guardarVendedor(user);
        if (toReturn != null) {
            // Llamar al método login
            LoginDTO loginDto = new LoginDTO();
            loginDto.setUsername(user.getUsername());
            loginDto.setPassword(user.getPassword());
            return usuariosController.login(loginDto, response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Vendedor> addVendedor(@RequestBody VendedorDTO vendedorDto) {

        try {
            Vendedor vendedor = new Vendedor();
            vendedor=DtoConverter.fromVendedorDTO(vendedorDto);
            vendedor.setRoles(Stream.of(UserRoles.VENDEDOR, UserRoles.USER).collect(Collectors.toSet()));

            if (aplicacionVendedor.guardar(vendedor)) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(vendedor);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null);
            }

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);

        }

    }

   

    @GetMapping("/allData")
    public List<Vendedor> verVendedoresListAll() {
        return aplicacionVendedor.obtenerTodos();
    }

    @GetMapping("/{id}/productos")
    public List<ProductoDTO> verProductosUsuario(@PathVariable int id) {
        Vendedor usuario = aplicacionVendedor.buscar(id);

        List<ProductoDTO> productos = usuario.getProductos().stream()
                .map(producto -> ProductoDtoConverter.fromProducto(producto))
                .collect(Collectors.toList());

                return productos;
       
    }
}
