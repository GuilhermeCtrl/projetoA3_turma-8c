package com.sistema.backend.controller;

import com.sistema.backend.model.Usuario;
import com.sistema.backend.model.Equipe;
import com.sistema.backend.repository.UsuarioRepository;
import com.sistema.backend.repository.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EquipeRepository equipeRepository;

    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    public Usuario criar(@RequestBody Usuario usuario) {
        if (usuario.getEquipes() != null) {
            usuario.setEquipes(usuario.getEquipes().stream()
                    .map(e -> equipeRepository.findById(e.getId())
                            .orElseThrow(() -> new RuntimeException("Equipe não encontrada: " + e.getId())))
                    .toList());
        }
        return usuarioRepository.save(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }



    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setCpf(usuarioAtualizado.getCpf());
            usuario.setEmail(usuarioAtualizado.getEmail());
            usuario.setCargo(usuarioAtualizado.getCargo());
            usuario.setLogin(usuarioAtualizado.getLogin());
            usuario.setSenha(usuarioAtualizado.getSenha());

            if (usuarioAtualizado.getEquipes() != null) {
                usuario.setEquipes(usuarioAtualizado.getEquipes().stream()
                        .map(e -> equipeRepository.findById(e.getId())
                                .orElseThrow(() -> new RuntimeException("Equipe não encontrada: " + e.getId())))
                        .toList());
            } else {
                usuario.setEquipes(null);
            }

            usuarioRepository.save(usuario);
            return ResponseEntity.ok(usuario);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
    // PATCH atualizar apenas equipes
    @PatchMapping("/{id}/equipes")
    public ResponseEntity<Usuario> atualizarEquipes(@PathVariable Long id, @RequestBody List<Equipe> novasEquipes) {
        return usuarioRepository.findById(id).map(usuario -> {
            if (novasEquipes != null) {
                usuario.setEquipes(novasEquipes.stream()
                        .map(e -> equipeRepository.findById(e.getId())
                                .orElseThrow(() -> new RuntimeException("Equipe não encontrada: " + e.getId())))
                        .toList());
            } else {
                usuario.setEquipes(null);
            }
            usuarioRepository.save(usuario);
            return ResponseEntity.ok(usuario);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
