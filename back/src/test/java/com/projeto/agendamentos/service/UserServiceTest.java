package com.projeto.agendamentos.service;

import com.projeto.agendamentos.dtos.user.UserResponse;
import com.projeto.agendamentos.exceptions.NotFoundIdException;
import com.projeto.agendamentos.model.security.User;
import com.projeto.agendamentos.repository.security.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUserName("dev_test");
        user.setEmail("dev@test.com");
    }

    @Nested
    @DisplayName("Testes de Busca por Username")
    class BuscaPorUsernameTests {

        @Test
        @DisplayName("Deve carregar UserDetails com sucesso")
        void loadUserByUsername_Sucesso() {
            when(userRepository.findByUsername("dev_test")).thenReturn(user);

            UserDetails result = userService.loadUserByUsername("dev_test");

            assertNotNull(result);
            assertEquals("dev_test", result.getUsername());
            verify(userRepository, times(1)).findByUsername("dev_test");
        }

        @Test
        @DisplayName("Deve lançar UsernameNotFoundException ao carregar UserDetails de usuário inexistente")
        void loadUserByUsername_UsuarioNaoEncontrado() {
            when(userRepository.findByUsername("invalido")).thenReturn(null);

            assertThrows(UsernameNotFoundException.class, 
                () -> userService.loadUserByUsername("invalido"));
            
            verify(userRepository, times(1)).findByUsername("invalido");
        }

        @Test
        @DisplayName("Deve retornar UserResponse ao buscar por username com sucesso")
        void findByUsername_Sucesso() {
            when(userRepository.findByUsername("dev_test")).thenReturn(user);

            UserResponse response = userService.findByUsername("dev_test");

            assertNotNull(response);
            assertEquals(1L, response.id());
            assertEquals("dev_test", response.username());
            assertEquals("dev@test.com", response.email());
        }

        @Test
        @DisplayName("Deve lançar UsernameNotFoundException ao buscar DTO por username inexistente")
        void findByUsername_UsuarioNaoEncontrado() {
            when(userRepository.findByUsername("invalido")).thenReturn(null);

            assertThrows(UsernameNotFoundException.class, 
                () -> userService.findByUsername("invalido"));
        }
    }

    @Nested
    @DisplayName("Testes de Busca por ID")
    class BuscaPorIdTests {

        @Test
        @DisplayName("Deve retornar UserResponse quando encontrar o ID")
        void findById_Sucesso() {
            when(userRepository.findById(1L)).thenReturn(Optional.of(user));

            UserResponse response = userService.findById(1L);

            assertNotNull(response);
            assertEquals(1L, response.id());
            assertEquals("dev_test", response.username());
            verify(userRepository, times(1)).findById(1L);
        }

        @Test
        @DisplayName("Deve lançar NotFoundIdException quando ID não existir")
        void findById_NaoEncontrado() {
            when(userRepository.findById(99L)).thenReturn(Optional.empty());

            assertThrows(NotFoundIdException.class, () -> userService.findById(99L));
            verify(userRepository, times(1)).findById(99L);
        }
    }

    @Nested
    @DisplayName("Testes de Atualização e Exclusão")
    class EscritaTests {


        @Test
        @DisplayName("Deve deletar usuário com sucesso")
        void deletarUsuario_Sucesso() {
            when(userRepository.findById(1L)).thenReturn(Optional.of(user));

            userService.deletarUsuario(1L);

            verify(userRepository, times(1)).deleteById(1L);
        }

        @Test
        @DisplayName("Deve lançar IllegalArgumentException para ID nulo ou negativo na exclusão")
        void deletarUsuario_IdInvalido() {
            assertThrows(IllegalArgumentException.class, () -> userService.deletarUsuario(null));
            assertThrows(IllegalArgumentException.class, () -> userService.deletarUsuario(-1L));

            verify(userRepository, never()).deleteById(any());
        }

        @Test
        @DisplayName("Deve lançar UsernameNotFoundException ao tentar deletar usuário inexistente")
        void deletarUsuario_UsuarioNaoEncontrado() {
            when(userRepository.findById(99L)).thenReturn(Optional.empty());

            assertThrows(UsernameNotFoundException.class, () -> userService.deletarUsuario(99L));

            verify(userRepository, never()).deleteById(any());
        }
    }
}