package com.sistema.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToMany(mappedBy = "equipes")
    private List<Usuario> usuarios;

    @ManyToOne
    @JoinColumn(name = "projeto_id")
    private Projeto projeto;
}
