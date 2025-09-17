package com.sistema.backend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private String dataInicio;
    private String dataFimPrevista;
    private String status; // planejado, em andamento, concluído, cancelado

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL)
    private List<Equipe> equipes;

    public Projeto() {}

    public Projeto(String nome, String descricao, String dataInicio, String dataFimPrevista, String status) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFimPrevista = dataFimPrevista;
        this.status = status;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getDataInicio() { return dataInicio; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }

    public String getDataFimPrevista() { return dataFimPrevista; }
    public void setDataFimPrevista(String dataFimPrevista) { this.dataFimPrevista = dataFimPrevista; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<Equipe> getEquipes() { return equipes; }
    public void setEquipes(List<Equipe> equipes) { this.equipes = equipes; }
}
