package com.projeto.agendamentos.service;

import com.projeto.agendamentos.dtos.sala.SalaResponse;
import com.projeto.agendamentos.exceptions.sala.SalaNaoEncontradaException;
import com.projeto.agendamentos.model.Sala;
import com.projeto.agendamentos.repository.SalaRepository;
import org.springframework.stereotype.Service;

@Service
public class SalaService {

    private final SalaRepository repository;

    public SalaService(SalaRepository repository) {
        this.repository = repository;
    }

    public void deletarSala(Long id) {
        repository.deleteById(id);
    }

    public void salvarSala(String nome, String localizacao, Integer capacidade) {
        
        validarDados(nome, localizacao, capacidade);

        var sala = new Sala();
        sala.setNome(nome);
        sala.setLocalizacao(localizacao);
        sala.setCapacidade(capacidade);

        repository.save(sala);
    }

    // Métodos privados auxiliares
    public void atualizarSala(Long id, String nome, String localizacao, Integer capacidade) {
        var salaExistente = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Sala não encontrada."));
        
        salaExistente.setNome(nome);
        salaExistente.setLocalizacao(localizacao);
        salaExistente.setCapacidade(capacidade);
        
        repository.save(salaExistente);
    }
    
    public Sala buscarSalaPorId(Long id) {
        return repository.findById(id)
        .orElseThrow(() -> new SalaNaoEncontradaException("Sala não encontrada."));
    }
    
    
    public Iterable<Sala> listarSalas() {
        return repository.findAll();
    }

    public boolean existeSalaComId(Long id) {
        return repository.existsById(id);
    }

    public boolean existeSalaComNome(String nome) {
        return repository.existsByNome(nome);
    }

    // Métodos privados auxiliares
    
    private void validarDados(String nome, String localizacao, Integer capacidade) {
        if(nome == null || nome.isBlank()) {
            throw new RuntimeException("Nome da sala não pode ser nulo ou vazio.");
        }
    
        if(localizacao == null || localizacao.isBlank()) {
            throw new RuntimeException("Localização da sala não pode ser nula ou vazia.");
        }
    
        if(capacidade == null || capacidade <= 0) {
            throw new RuntimeException("Capacidade da sala não pode ser nula, zero ou negativa.");
        }
    }

    private void addHateoasLinks(SalaResponse salaResponse) {

    }
    
}
