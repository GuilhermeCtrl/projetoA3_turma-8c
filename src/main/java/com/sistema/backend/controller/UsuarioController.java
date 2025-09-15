package com.sistema.backend.controller;

import com.sistema.backend.model.Usuario;
import com.sistema.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios") // endpoint base
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Listar todos
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Cadastrar
    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
