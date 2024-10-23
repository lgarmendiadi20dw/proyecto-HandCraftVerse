package com.proyeto.hand_craft_verse.controladores;

import com.proyeto.hand_craft_verse.aplicacion.IAplicacion;
import com.proyeto.hand_craft_verse.dominio.pedidos.EstadoPedido;
import com.proyeto.hand_craft_verse.dominio.pedidos.Pedido;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidosController {

    @Autowired
    IAplicacion<Pedido> aplicacionPedido;

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> viewPedido(@PathVariable int id) {
        Pedido pedido = aplicacionPedido.buscar(id);
        if (pedido != null) {
            return ResponseEntity.ok(pedido);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePedidoById(@PathVariable int id) {
        if (aplicacionPedido.eliminar(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Pedido> addPedido(@RequestBody Pedido pedido) {
        try {
            if (aplicacionPedido.guardar(pedido)) {
                return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updatePedido(@PathVariable int id, @RequestBody Pedido pedido) {
        Pedido existingPedido = aplicacionPedido.buscar(id);
        if (existingPedido != null) {
            existingPedido.setFechaCompra(pedido.getFechaCompra());
            existingPedido.setNumeroSeguimiento(pedido.getNumeroSeguimiento());
            existingPedido.setEstado(pedido.getEstado());
            existingPedido.setDireccion(pedido.getDireccion());
            existingPedido.setCosteTotal(pedido.getCosteTotal());
            existingPedido.setPedidoProductos(pedido.getPedidoProductos()); // Asegúrate de que se maneje correctamente

            if (aplicacionPedido.actualizar(existingPedido) != null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/updateEstado/{id}")
    public ResponseEntity<Void> updateEstadoPedido(@PathVariable int id, @RequestBody EstadoPedido nuevoEstado) {
        Pedido existingPedido = aplicacionPedido.buscar(id);
        if (existingPedido != null) {
            existingPedido.setEstado(nuevoEstado);

            if (aplicacionPedido.actualizar(existingPedido) != null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    public List<Pedido> verPedidosList() {
        return aplicacionPedido.obtenerTodos();

    }
}
