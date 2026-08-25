package com.projeto.agendamentos.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table (name="salas")
public class Sala  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private String localizacao;

    @Column(nullable = false)
    private String responsavel;

    @Column()
    private Integer capacidade;


    public Sala() {
    }

    public Sala(Long id, String nome, String localizacao,
                String responsavel, Integer capacidade) {
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
        this.responsavel = responsavel;
        this.capacidade = capacidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Sala sala = (Sala) o;
        return Objects.equals(id, sala.id) && Objects.equals(nome, sala.nome) && Objects.equals(localizacao, sala.localizacao) && Objects.equals(responsavel, sala.responsavel) && Objects.equals(capacidade, sala.capacidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, localizacao, responsavel, capacidade);
    }
}