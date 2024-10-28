package com.proyeto.hand_craft_verse.aplicacion;

import com.proyeto.hand_craft_verse.dominio.usuarios.Usuario;
import com.proyeto.hand_craft_verse.dto.UserGetDto;
import com.proyeto.hand_craft_verse.dto.UserRegisterDto;
import com.proyeto.hand_craft_verse.dto.Converter.DtoConverter;
import com.proyeto.hand_craft_verse.dto.Converter.UserDtoConverter;
import com.proyeto.hand_craft_verse.persistencia.IPersistencia;

public class AplicacionUsuario extends Aplicacion<Usuario>{
    UserDtoConverter userDtoConverter;

    public AplicacionUsuario(IPersistencia<Usuario> persistencia, UserDtoConverter userDtoConverter) {
        super(persistencia);
        this.userDtoConverter=userDtoConverter;
    }

    @Override
    public boolean guardar(Usuario usuario){
        return false;
    }

    public UserGetDto guardar(UserRegisterDto usuario){
        Usuario to_returnUsuario=null;
        try {
            to_returnUsuario= userDtoConverter.toUser(usuario);
            if(usuario.getPassword().compareTo(usuario.getPasswordConfirm())==0 && usuario.getEmail().compareTo(usuario.getEmailConfirm())==0){
                return userDtoConverter.fromUser(to_returnUsuario);
            }
            return null;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
    
    @Override
    public Usuario buscarPorNombre(String nombre){
        return persistencia.query("username", nombre).getFirst();
    }
    
    // public boolean verifyEmail(String email){
    //     if(email.contains(" ")){
    //         return false;
    //     }

    //     if(email.contains("@")){
    //         String cadena[]=email.split("@");
    //         return true;
    //     }
    //     return false;
    // }
    
    
}
