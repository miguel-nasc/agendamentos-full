package com.projeto.agendamentos.service.security;

import com.projeto.agendamentos.dtos.security.AccountCredentialsDTO;
import com.projeto.agendamentos.dtos.security.TokenDTO;
import com.projeto.agendamentos.dtos.user.UserRequest;
import com.projeto.agendamentos.model.security.User;
import com.projeto.agendamentos.repository.security.UserRepository;
import com.projeto.agendamentos.security.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider tokenProvider;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    private AccountCredentialsDTO criarCredenciais(String username, String password) {
        AccountCredentialsDTO credentials = new AccountCredentialsDTO();
        credentials.setUsername(username);
        credentials.setPassword(password);
        return credentials;
    }

    @Nested
    @DisplayName("Testes de Autenticação (SignIn)")
    class SignInTestes {

        @Test
        @DisplayName("Deve realizar login e retornar TokenDTO quando as credenciais forem válidas")
        void deveRealizarSignInComSucesso() {
            var credentials = criarCredenciais("dev_user", "password123");
            var user = new User();
            user.setUserName("dev_user");
            user.setPermissions(Collections.emptyList()); // Inicializa a lista de permissões

            var expectedToken = new TokenDTO();

            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .thenReturn(null);
            when(userRepository.findByUsername("dev_user")).thenReturn(user);
            when(tokenProvider.createAccessToken("dev_user", user.getRoles())).thenReturn(expectedToken);

            TokenDTO result = authService.signIn(credentials);

            assertNotNull(result);
            assertEquals(expectedToken, result);
            verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
            verify(userRepository, times(1)).findByUsername("dev_user");
            verify(tokenProvider, times(1)).createAccessToken("dev_user", user.getRoles());
        }

        @Test
        @DisplayName("Deve retornar null quando a autenticação falhar com exceção de credenciais")
        void deveRetornarNullQuandoAutenticacaoFalhar() {
            var credentials = criarCredenciais("dev_user", "senha_errada");

            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .thenThrow(new BadCredentialsException("Credenciais inválidas"));

            TokenDTO result = authService.signIn(credentials);

            assertNull(result);
            verify(userRepository, never()).findByUsername(anyString());
            verify(tokenProvider, never()).createAccessToken(anyString(), anyList());
        }

        @Test
        @DisplayName("Deve lançar UsernameNotFoundException quando o usuário não for encontrado no banco após autenticar")
        void deveLancarExcecaoQuandoUsuarioNaoEncontradoNoBanco() {
            var credentials = criarCredenciais("usuario_inexistente", "password123");

            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .thenReturn(null);
            when(userRepository.findByUsername("usuario_inexistente")).thenReturn(null);

            var ex = assertThrows(UsernameNotFoundException.class, () -> authService.signIn(credentials));

            assertTrue(ex.getMessage().contains("usuario_inexistente"));
            verify(tokenProvider, never()).createAccessToken(anyString(), anyList());
        }
    }

    @Nested
    @DisplayName("Testes de Cadastro de Usuário (SignUp)")
    class SignUpTestes {

        @Test
        @DisplayName("Deve cadastrar um novo usuário com sucesso")
        void deveCadastrarUsuarioComSucesso() {
            var request = new UserRequest("dev_user", "Dev User Full", "dev@email.com", "123456");

            when(userRepository.findByUsername("dev_user")).thenReturn(null);
            when(userRepository.findByEmail("dev@email.com")).thenReturn(null);

            authService.signup(request);

            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository, times(1)).save(userCaptor.capture());

            User userSalvo = userCaptor.getValue();
            assertEquals("dev_user", userSalvo.getUserName());
            assertEquals("Dev User Full", userSalvo.getFullName());
            assertEquals("dev@email.com", userSalvo.getEmail());
            assertNotNull(userSalvo.getPassword());
        }

        @Test
        @DisplayName("Deve lançar IllegalArgumentException quando o username já existir")
        void deveLancarExcecaoQuandoUsernameJaExistir() {
            var request = new UserRequest("dev_user", "Dev User Full", "dev@email.com", "123456");

            when(userRepository.findByUsername("dev_user")).thenReturn(new User());

            var ex = assertThrows(IllegalArgumentException.class, () -> authService.signup(request));

            assertEquals("Username dev_user already exists", ex.getMessage());
            verify(userRepository, never()).save(any());
        }

        @Test
        @DisplayName("Deve lançar IllegalArgumentException quando o e-mail já existir")
        void deveLancarExcecaoQuandoEmailJaExistir() {
            var request = new UserRequest("dev_user", "Dev User Full", "existente@email.com", "123456");

            when(userRepository.findByUsername("dev_user")).thenReturn(null);
            when(userRepository.findByEmail("existente@email.com")).thenReturn(new User());

            var ex = assertThrows(IllegalArgumentException.class, () -> authService.signup(request));

            assertEquals("Email existente@email.com already exists", ex.getMessage());
            verify(userRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Testes de Refresh Token")
    class RefreshTokenTestes {

        @Test
        @DisplayName("Deve renovar o token com sucesso quando o usuário existir")
        void deveRenovarTokenComSucesso() {
            var username = "dev_user";
            var refreshToken = "valid_refresh_token";
            var expectedToken = new TokenDTO();

            when(userRepository.findByUsername(username)).thenReturn(new User());
            when(tokenProvider.refreshToken(refreshToken)).thenReturn(expectedToken);

            TokenDTO result = authService.refreshToken(username, refreshToken);

            assertNotNull(result);
            assertEquals(expectedToken, result);
            verify(tokenProvider, times(1)).refreshToken(refreshToken);
        }

        @Test
        @DisplayName("Deve lançar UsernameNotFoundException ao tentar renovar token de usuário inexistente")
        void deveLancarExcecaoAoRenovarTokenDeUsuarioInexistente() {
            var username = "usuario_inexistente";
            var refreshToken = "valid_refresh_token";

            when(userRepository.findByUsername(username)).thenReturn(null);

            var ex = assertThrows(UsernameNotFoundException.class, () -> authService.refreshToken(username, refreshToken));

            assertTrue(ex.getMessage().contains("usuario_inexistente"));
            verify(tokenProvider, never()).refreshToken(anyString());
        }
    }
}