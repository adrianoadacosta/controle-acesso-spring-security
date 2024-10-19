package com.example.adrianocosta.controle_acesso_spring_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            SenhaMasterAuthenticationProvider senhaMasterAuthenticationProvider,
            CustomAuthenticationProvider customAuthenticationProvider,
            CustomFilter customFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // disabilida o crsf da API
                .authorizeHttpRequests(customizer -> { // lista o que cada um vai poder acessar, portege as rotas
                    customizer.requestMatchers("/public").permitAll();
                    customizer.anyRequest().authenticated(); // sempre por último caso esqueca de alguma linha de securança
                })
                .httpBasic(Customizer.withDefaults()) // http basic padrão
                .formLogin(Customizer.withDefaults()) // formulario de ‘login’ padrão
                .authenticationProvider(senhaMasterAuthenticationProvider)
                .authenticationProvider(customAuthenticationProvider)
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean // cria em memororia usuarios e as senhas que vamos colocar
    public UserDetailsService userDetailsService(){
        UserDetails commonUser = User.builder()
                .username("user")
                .password(passwordEncoder().encode("123")) // codifica a senha
                .roles("USER") // grupo de usuario
                .build();

        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(commonUser, adminUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        return new GrantedAuthorityDefaults("");
    }
}
