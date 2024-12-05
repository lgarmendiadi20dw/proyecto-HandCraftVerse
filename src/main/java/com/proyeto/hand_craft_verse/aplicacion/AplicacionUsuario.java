package com.proyeto.hand_craft_verse.aplicacion;

import java.util.List;

import com.proyeto.hand_craft_verse.dominio.usuarios.Admin;
import com.proyeto.hand_craft_verse.dominio.usuarios.Usuario;
import com.proyeto.hand_craft_verse.dominio.usuarios.Vendedor;
import com.proyeto.hand_craft_verse.dto.UserGetDto;
import com.proyeto.hand_craft_verse.dto.UserRegisterDto;
import com.proyeto.hand_craft_verse.dto.Converter.UserDtoConverter;
import com.proyeto.hand_craft_verse.persistencia.IPersistencia;

public class AplicacionUsuario extends Aplicacion<Usuario> {
    UserDtoConverter userDtoConverter;

    public AplicacionUsuario(IPersistencia<Usuario> persistencia, UserDtoConverter userDtoConverter) {
        super(persistencia);
        this.userDtoConverter = userDtoConverter;
    }

    @Override
    public boolean guardar(Usuario usuario) {
        return false;
    }

    public UserGetDto guardar(UserRegisterDto usuario) {
        Usuario usuarioADevolver = null;

        try {
            if ((usuario.getPassword().compareTo(usuario.getPasswordConfirm()) == 0) &&
                    usuario.getEmail().compareTo(usuario.getEmailConfirm()) == 0) {

                usuarioADevolver = userDtoConverter.toUser(usuario);
                    usuarioADevolver.setImagen("userIcon.webp");
                if (persistencia.guardar(usuarioADevolver)) {
                    return userDtoConverter.toUserGetDto(usuarioADevolver);
                } else {

                    return null;

                }

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserGetDto guardarAdmin(UserRegisterDto usuario) {
        Admin usuarioADevolver = null;

        try {
            if ((usuario.getPassword().compareTo(usuario.getPasswordConfirm()) == 0) &&
                    usuario.getEmail().compareTo(usuario.getEmailConfirm()) == 0) {

                usuarioADevolver = userDtoConverter.toAdminUser(usuario);
                usuarioADevolver.setImagen("userIcon.webp");
                if (persistencia.guardar(usuarioADevolver)) {
                    return userDtoConverter.toUserGetDto(usuarioADevolver);
                } else {

                    return null;

                }

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public UserGetDto guardarVendedor(UserRegisterDto usuario) {
        Vendedor usuarioADevolver = null;

        try {
            if ((usuario.getPassword().compareTo(usuario.getPasswordConfirm()) == 0) &&
                    usuario.getEmail().compareTo(usuario.getEmailConfirm()) == 0) {

                usuarioADevolver = userDtoConverter.toVendedorUser(usuario);
                usuarioADevolver.setImagen("userIcon.webp");
                if (persistencia.guardar(usuarioADevolver)) {
                    return userDtoConverter.toUserGetDto(usuarioADevolver);
                } else {

                    return null;

                }

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Usuario buscarPorNombre(String nombre) {
        return persistencia.query("username", nombre).getFirst();
    }

    public boolean verifyEmail(String email) {
        if (email.contains(" ")) {
            return false;
        }
        if (email.contains("@")) {
            String cadena[] = email.split("@");
            if (cadena.length == 2) {
                if (cadena[1].contains(".")) {
                    return true;
                }
            }
        }

        return false;

    }

    // @Override
    // public boolean eliminar(Object id) {

    // Usuario t = persistencia.obtener(id);

    // if(t == null)
    // {
    // return false;
    // }
    // else{
    // return persistencia.eliminar(t);
    // }
    // }

    // @Override
    // public java.util.List<Usuario> obtenerTodos() {
    // return persistencia.obtenerTodos();
    // }

    // @Override
    // public Usuario actualizar(Usuario t) {

    // if(persistencia.actualizar(t))
    // {
    // return t;
    // }
    // return null;
    // }

    // @Override
    // public List<Object[]> obtenerDatosColumnas(String nombreColumna1, String
    // nombreColumna2) {
    // return persistencia.obtenerDatosColumnas( nombreColumna1, nombreColumna2);
    // }
}
