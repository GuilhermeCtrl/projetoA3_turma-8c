package com.sistema.backend.controller;

import com.sistema.backend.model.Equipe;
import com.sistema.backend.model.Usuario;
import com.sistema.backend.repository.UsuarioRepository;
import com.sistema.backend.repository.EquipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EquipeRepository equipeRepository;

    // Listar todos os usuários
    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    // Criar um usuário
    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        // Se a equipe foi informada, verifica se existe
        if (usuario.getEquipe() != null) {
            Long equipeId = usuario.getEquipe().getId();
            Equipe equipe = equipeRepository.findById(equipeId)
                    .orElseThrow(() -> new RuntimeException("Equipe não encontrada com id: " + equipeId));
            usuario.setEquipe(equipe);
        }
        Usuario salvo = usuarioRepository.save(usuario);
        return ResponseEntity.ok(salvo);
    }

    // Deletar usuário pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Atualizar usuário completamente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(
            @PathVariable Long id,
            @RequestBody Usuario usuarioAtualizado) {

        return usuarioRepository.findById(id).map(usuarioExistente -> {
            usuarioExistente.setNome(usuarioAtualizado.getNome());
            usuarioExistente.setCpf(usuarioAtualizado.getCpf());
            usuarioExistente.setEmail(usuarioAtualizado.getEmail());
            usuarioExistente.setCargo(usuarioAtualizado.getCargo());
            usuarioExistente.setLogin(usuarioAtualizado.getLogin());
            usuarioExistente.setSenha(usuarioAtualizado.getSenha());

            if (usuarioAtualizado.getEquipe() != null) {
                Long equipeId = usuarioAtualizado.getEquipe().getId();
                Equipe equipe = equipeRepository.findById(equipeId)
                        .orElseThrow(() -> new RuntimeException("Equipe não encontrada com id: " + equipeId));
                usuarioExistente.setEquipe(equipe);
            } else {
                usuarioExistente.setEquipe(null);
            }

            usuarioRepository.save(usuarioExistente);
            return ResponseEntity.ok(usuarioExistente);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Atualizar apenas a equipe de um usuário (PATCH)
    @PatchMapping("/{id}/equipe")
    public ResponseEntity<Usuario> atualizarEquipe(@PathVariable Long id, @RequestBody Equipe equipe) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = usuarioOptional.get();

        if (equipe != null && equipe.getId() != null) {
            Equipe equipeExistente = equipeRepository.findById(equipe.getId())
                    .orElseThrow(() -> new RuntimeException("Equipe não encontrada com id: " + equipe.getId()));
            usuario.setEquipe(equipeExistente);
        } else {
            usuario.setEquipe(null);
        }

        usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuario);
    }
}
