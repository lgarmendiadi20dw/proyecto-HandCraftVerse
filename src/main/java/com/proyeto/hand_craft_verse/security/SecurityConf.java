package com.proyeto.hand_craft_verse.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.proyeto.hand_craft_verse.aplicacion.AplicacionUsuario;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConf {

    private PasswordEncoder passwordEncoder;
    private final CustomUserDetailService customUserDetailService;

    @Bean
    public UserDetailsService userDetailsService(AplicacionUsuario persistenciaUsuario) {
        return new CustomUserDetailService(persistenciaUsuario);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

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