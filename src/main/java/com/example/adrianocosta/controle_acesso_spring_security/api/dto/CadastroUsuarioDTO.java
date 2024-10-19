package com.example.adrianocosta.controle_acesso_spring_security.api.dto;

import com.example.adrianocosta.controle_acesso_spring_security.domain.entity.Usuario;
import lombok.Data;

import java.util.List;

@Data
public class CadastroUsuarioDTO {
    private Usuario usuario;
    private List<String> permissoes;
}
