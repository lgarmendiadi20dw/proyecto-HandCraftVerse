package com.proyeto.hand_craft_verse.dto.Converter;

import com.proyeto.hand_craft_verse.aplicacion.Aplicacion;
import com.proyeto.hand_craft_verse.dominio.direccion.Direccion;
import com.proyeto.hand_craft_verse.dominio.productos.Categoria;
import com.proyeto.hand_craft_verse.dominio.productos.Colore;
import com.proyeto.hand_craft_verse.dominio.productos.Multimedia;
import com.proyeto.hand_craft_verse.dominio.productos.Producto;
import com.proyeto.hand_craft_verse.dominio.usuarios.Vendedor;
import com.proyeto.hand_craft_verse.dto.CategoriaDTO;
import com.proyeto.hand_craft_verse.dto.ColoreDTO;
import com.proyeto.hand_craft_verse.dto.DireccionDTO;
import com.proyeto.hand_craft_verse.dto.VendedorDTO;
import com.proyeto.hand_craft_verse.dto.Productos.MultimediaDTO;

import org.springframework.stereotype.Component;

@Component
public class DtoConverter {

    
        public static VendedorDTO fromVendedor(Vendedor vendedor) {
            return VendedorDTO.builder()
                    .dni(vendedor.getDni())
                    .nombreUsuario(vendedor.getUsername())
                    .nombre(vendedor.getNombre())
                    .apellidos(vendedor.getApellidos())
                    .email(vendedor.getEmail())
                    .imagen(vendedor.getImagen())
                    .password(vendedor.getPassword())
                    .telefono(vendedor.getTelefono())
                    .numVentas(vendedor.getNum_ventas())
                    .descripcion(vendedor.getDescripcion())
                    .build();
        }
    
        public static Vendedor fromVendedorDTO(VendedorDTO vendedorDTO) {
            Vendedor vendedor = new Vendedor();
            vendedor.setUsername(vendedorDTO.getNombreUsuario());
            vendedor.setEmail(vendedorDTO.getEmail());
            vendedor.setPassword(vendedorDTO.getPassword());
            vendedor.setTelefono(vendedorDTO.getTelefono());
            vendedor.setNum_ventas(vendedorDTO.getNumVentas());
            vendedor.setNombre(vendedorDTO.getNombre());
            vendedor.setApellidos(vendedorDTO.getApellidos());
            vendedor.setDescripcion(vendedorDTO.getDescripcion());
            vendedor.setImagen(vendedorDTO.getImagen());
            return vendedor;
        }
    
        public static ColoreDTO fromColore(Colore colore) {
            return ColoreDTO.builder()
                    .hex(colore.getHex())
                    .nombre(colore.getNombre())
                    .build();
        }
    
        public static Colore fromColoreDTO(ColoreDTO coloreDTO) {
            Colore colore = new Colore();
            colore.setHex(coloreDTO.getHex());
            colore.setNombre(coloreDTO.getNombre());
            return colore;
        }
    
        public static CategoriaDTO fromCategoria(Categoria categoria) {
            return CategoriaDTO.builder()
                    .nombre(categoria.getNombre())
                    .descripcion(categoria.getDescripcion())
                    .build();
        }
    
        public static Categoria fromCategoriaDTO(CategoriaDTO categoriaDTO) {
            Categoria categoria = new Categoria();
            categoria.setNombre(categoriaDTO.getNombre());
            categoria.setDescripcion(categoriaDTO.getDescripcion());
            return categoria;
        }
    
        public static MultimediaDTO fromMultimedia(Multimedia multimedia) {
            return MultimediaDTO.builder()
                    .url(multimedia.getUrl())
                    .alt(multimedia.getAlt())
                    .nombreArchivo(multimedia.getNombreArchivo())
                    .producto(multimedia.getProducto().getId())
                    .build();
        }
    
        public static Multimedia fromMultimediaDTO(MultimediaDTO multimediaDTO) {
            return Multimedia.builder()
                    .url(multimediaDTO.getUrl())
                    .alt(multimediaDTO.getAlt())
                    .nombreArchivo(multimediaDTO.getNombreArchivo())
                    .build();
        }
    
        


    public static DireccionDTO fromDireccion(Direccion direccion) {
        DireccionDTO dto = new DireccionDTO();
        dto.setId(direccion.getId());
        dto.setPais(direccion.getPais());
        dto.setProvincia(direccion.getProvincia());
        dto.setMunicipio(direccion.getMunicipio());
        dto.setCalle(direccion.getCalle());
        dto.setPrefijo(direccion.getPrefijo());
        dto.setNumTelefono(direccion.getNumTelefono());
        dto.setDestinatario(direccion.getDestinatario());
        dto.setTipoDireccion(direccion.getTipoDireccion());
        return dto;
    }

    public static Direccion fromDireccionDTO(DireccionDTO dto) {
        Direccion direccion = new Direccion();
        direccion.setId(dto.getId());
        direccion.setPais(dto.getPais());
        direccion.setProvincia(dto.getProvincia());
        direccion.setMunicipio(dto.getMunicipio());
        direccion.setCalle(dto.getCalle());
        direccion.setPrefijo(dto.getPrefijo());
        direccion.setNumTelefono(dto.getNumTelefono());
        direccion.setDestinatario(dto.getDestinatario());
        direccion.setTipoDireccion(dto.getTipoDireccion());
        return direccion;
    }
}
