package com.example.adrianocosta.controle_acesso_spring_security.domain.repository;

import com.example.adrianocosta.controle_acesso_spring_security.domain.entity.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrupoRepository extends JpaRepository<Grupo, String> {
    Optional<Grupo> findByNome(String nome);
}
