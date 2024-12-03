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
import com.proyeto.hand_craft_verse.dto.UserGetDto;
import com.proyeto.hand_craft_verse.dto.UserRegisterDto;
import com.proyeto.hand_craft_verse.dto.VendedorDTO;
import com.proyeto.hand_craft_verse.dto.Converter.DtoConverter;
import com.proyeto.hand_craft_verse.dto.Converter.ProductoDtoConverter;
import com.proyeto.hand_craft_verse.dto.Productos.ProductoDTO;

@RestController
@RequestMapping("/member/seller")
public class VendedorController {
    @Autowired
    IAplicacion<Vendedor> aplicacionVendedor;

    @Autowired
    private AplicacionUsuario aplicacionUsuario;

    @GetMapping("/{id}")
    public Vendedor viewMyPorfile(@PathVariable int id) {
        return aplicacionVendedor.buscar(id);
    }

    @PostMapping("/registrar")
    public ResponseEntity<UserGetDto> registrar(@RequestBody UserRegisterDto user) {
        UserGetDto toReturn = aplicacionUsuario.guardarVendedor(user);
        if (toReturn != null) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(toReturn);

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

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Vendedor entity) {

        if (aplicacionVendedor.actualizar(entity) != null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @GetMapping("/all")
    public List<Map<String, Object>> verVendedoresList() {
        return aplicacionVendedor.obtenerDatosColumnas("id", "username")
                .stream()
                .map(fila -> Map.of("id", fila[0], "username", fila[1]))
                .toList();
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
