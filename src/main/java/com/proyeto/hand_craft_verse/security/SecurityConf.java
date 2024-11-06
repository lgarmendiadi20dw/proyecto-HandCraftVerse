package com.proyeto.hand_craft_verse.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.proyeto.hand_craft_verse.aplicacion.AplicacionUsuario;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConf {

    private PasswordEncoder passwordEncoder;

    @Bean
    public UserDetailsService userDetailsService(AplicacionUsuario persistenciaUsuario) {
        return new CustomUserDetailService(persistenciaUsuario);
    }

    // @Bean
    // public UserDetailsService userDetailsService() {
    // InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

    // UserDetails usuario1 =
    // User.builder().username("legadilo2").password(passwordEncoder.encode("1234"))
    // .roles("USER").build(),
    // usuario2 =
    // User.builder().username("legadilo3").password(passwordEncoder.encode("1234")).roles("ADMIN")
    // .build();

    // manager.createUser(usuario1);
    // manager.createUser(usuario2);
    // return manager;
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(requests ->
        requests.anyRequest().permitAll())
        .httpBasic(Customizer.withDefaults());

        // http.csrf(AbstractHttpConfigurer::disable)
        //         .authorizeHttpRequests(requests -> requests.requestMatchers(HttpMethod.GET,"/member/**").authenticated()
        //         .requestMatchers(HttpMethod.POST,"/productos/add").hasRole("VENDEDOR")
        //         .requestMatchers("/security/**").hasAnyRole("ADMIN","USER")
        //                 .anyRequest().permitAll())
        //         .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}