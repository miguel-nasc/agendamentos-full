package com.projeto.agendamentos.service;

import com.projeto.agendamentos.exceptions.sala.SalaNaoEncontradaException;
import com.projeto.agendamentos.model.Sala;
import com.projeto.agendamentos.repository.SalaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalaServiceTest {

    @Mock
    private SalaRepository repository;

    @InjectMocks
    private SalaService salaService;

    @Nested
    @DisplayName("Testes de Cadastro de Sala")
    class SalvarSalaTestes {

        @Test
        @DisplayName("Deve salvar sala com sucesso quando os dados forem válidos")
        void deveSalvarSalaComSucesso() {
            salaService.salvarSala("Auditório Main", "Bloco A", 100);

            ArgumentCaptor<Sala> captor = ArgumentCaptor.forClass(Sala.class);
            verify(repository, times(1)).save(captor.capture());

            Sala salaSalva = captor.getValue();
            assertEquals("Auditório Main", salaSalva.getNome());
            assertEquals("Bloco A", salaSalva.getLocalizacao());
            assertEquals(100, salaSalva.getCapacidade());
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar salvar com nome nulo ou em branco")
        void deveLancarExcecaoParaNomeInvalido() {
            var exNull = assertThrows(RuntimeException.class, () -> salaService.salvarSala(null, "Bloco A", 10));
            var exBlank = assertThrows(RuntimeException.class, () -> salaService.salvarSala("   ", "Bloco A", 10));

            assertEquals("Nome da sala não pode ser nulo ou vazio.", exNull.getMessage());
            assertEquals("Nome da sala não pode ser nulo ou vazio.", exBlank.getMessage());
            verify(repository, never()).save(any());
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar salvar com localização nula ou em branco")
        void deveLancarExcecaoParaLocalizacaoInvalida() {
            var exNull = assertThrows(RuntimeException.class, () -> salaService.salvarSala("Lab 1", null, 10));
            var exBlank = assertThrows(RuntimeException.class, () -> salaService.salvarSala("Lab 1", "", 10));

            assertEquals("Localização da sala não pode ser nula ou vazia.", exNull.getMessage());
            assertEquals("Localização da sala não pode ser nula ou vazia.", exBlank.getMessage());
            verify(repository, never()).save(any());
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar salvar com capacidade nula, zero ou negativa")
        void deveLancarExcecaoParaCapacidadeInvalida() {
            var exNull = assertThrows(RuntimeException.class, () -> salaService.salvarSala("Lab 1", "Bloco A", null));
            var exZero = assertThrows(RuntimeException.class, () -> salaService.salvarSala("Lab 1", "Bloco A", 0));
            var exNeg = assertThrows(RuntimeException.class, () -> salaService.salvarSala("Lab 1", "Bloco A", -5));

            assertEquals("Capacidade da sala não pode ser nula, zero ou negativa.", exNull.getMessage());
            assertEquals("Capacidade da sala não pode ser nula, zero ou negativa.", exZero.getMessage());
            assertEquals("Capacidade da sala não pode ser nula, zero ou negativa.", exNeg.getMessage());
            verify(repository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Testes de Busca por Sala")
    class BuscarSalaTestes {

        @Test
        @DisplayName("Deve retornar a sala por ID quando existir")
        void deveRetornarSalaPorId() {
            var sala = new Sala();
            sala.setNome("Lab de Informática");

            when(repository.findById(1L)).thenReturn(Optional.of(sala));

            var resultado = salaService.buscarSalaPorId(1L);

            assertNotNull(resultado);
            assertEquals("Lab de Informática", resultado.getNome());
            verify(repository, times(1)).findById(1L);
        }

        @Test
        @DisplayName("Deve lançar SalaNaoEncontradaException quando a sala não existir")
        void deveLancarExcecaoQuandoSalaNaoEncontrada() {
            when(repository.findById(99L)).thenReturn(Optional.empty());

            var ex = assertThrows(SalaNaoEncontradaException.class, () -> salaService.buscarSalaPorId(99L));

            assertEquals("Sala não encontrada.", ex.getMessage());
            verify(repository, times(1)).findById(99L);
        }
    }

    @Nested
    @DisplayName("Testes de Atualização de Sala")
    class AtualizarSalaTestes {

        @Test
        @DisplayName("Deve atualizar a sala com sucesso quando o ID existir")
        void deveAtualizarSalaComSucesso() {
            var salaExistente = new Sala();
            salaExistente.setNome("Nome Antigo");
            salaExistente.setLocalizacao("Bloco A");
            salaExistente.setCapacidade(20);

            when(repository.findById(1L)).thenReturn(Optional.of(salaExistente));

            salaService.atualizarSala(1L, "Nome Novo", "Bloco B", 30);

            verify(repository, times(1)).save(salaExistente);
            assertEquals("Nome Novo", salaExistente.getNome());
            assertEquals("Bloco B", salaExistente.getLocalizacao());
            assertEquals(30, salaExistente.getCapacidade());
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar atualizar sala inexistente")
        void deveLancarExcecaoAoAtualizarInexistente() {
            when(repository.findById(1L)).thenReturn(Optional.empty());

            var ex = assertThrows(RuntimeException.class, () -> salaService.atualizarSala(1L, "Nome", "Bloco A", 10));

            assertEquals("Sala não encontrada.", ex.getMessage());
            verify(repository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Testes de Exclusão e Consultas Auxiliares")
    class OutrosTestes {

        @Test
        @DisplayName("Deve deletar sala invocando o repository")
        void deveDeletarSala() {
            salaService.deletarSala(1L);
            verify(repository, times(1)).deleteById(1L);
        }

        @Test
        @DisplayName("Deve listar todas as salas")
        void deveListarSalas() {
            when(repository.findAll()).thenReturn(List.of(new Sala(), new Sala()));

            var resultado = salaService.listarSalas();

            assertNotNull(resultado);
            verify(repository, times(1)).findAll();
        }

        @Test
        @DisplayName("Deve retornar true se a sala existir por ID")
        void deveVerificarExistenciaPorId() {
            when(repository.existsById(1L)).thenReturn(true);

            boolean existe = salaService.existeSalaComId(1L);

            assertTrue(existe);
            verify(repository, times(1)).existsById(1L);
        }

        @Test
        @DisplayName("Deve retornar true se a sala existir por nome")
        void deveVerificarExistenciaPorNome() {
            when(repository.existsByNome("Auditório")).thenReturn(true);

            boolean existe = salaService.existeSalaComNome("Auditório");

            assertTrue(existe);
            verify(repository, times(1)).existsByNome("Auditório");
        }
    }
}