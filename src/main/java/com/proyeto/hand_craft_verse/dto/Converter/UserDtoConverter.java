package com.proyeto.hand_craft_verse.dto.Converter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.proyeto.hand_craft_verse.dominio.usuarios.UserRoles;
import com.proyeto.hand_craft_verse.dominio.usuarios.Usuario;
import com.proyeto.hand_craft_verse.dto.UserGetDto;
import com.proyeto.hand_craft_verse.dto.UserRegisterDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDtoConverter {

    private PasswordEncoder passwordEncoder;

    public  Usuario toUser(UserRegisterDto usuarioDTO)
    {
        Usuario respuesta = Usuario.builder().
                                username(usuarioDTO.getUsername()).
                                password(passwordEncoder.encode(usuarioDTO.getPassword())).
                                email(usuarioDTO.getEmail()).
                                roles(Stream.of(UserRoles.USER).collect(Collectors.toSet())).
                                //para los vendedores -> roles(Stream.of(UserRoles.USER, UserRoles.VENDEDOR).collect(Collectors.toSet())).
                                build();
        

                                    

        return respuesta;
    }

    public UserGetDto fromUser(Usuario usuario)
    {
        UserGetDto respuesta = UserGetDto.builder().
                                username(usuario.getUsername()).
                                email(usuario.getEmail()).
                                roles(usuario.getRoles().toString()).
                                build();
        

                                    

        return respuesta;
    }
    
}