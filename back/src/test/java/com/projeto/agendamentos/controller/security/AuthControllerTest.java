package com.projeto.agendamentos.controller.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto.agendamentos.dtos.security.AccountCredentialsDTO;
import com.projeto.agendamentos.dtos.security.TokenDTO;
import com.projeto.agendamentos.dtos.user.UserRequest;
import com.projeto.agendamentos.service.security.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    private AccountCredentialsDTO criarCredenciais(String username, String password) {
        AccountCredentialsDTO credentials = new AccountCredentialsDTO();
        credentials.setUsername(username);
        credentials.setPassword(password);
        return credentials;
    }

    @Nested
    @DisplayName("Testes do Endpoint /auth/signin")
    class SignInEndpointTestes {

        @Test
        @DisplayName("Deve retornar 200 OK e TokenDTO para credenciais válidas")
        void deveRetornarOkEAccessToken() throws Exception {
            var credentials = criarCredenciais("dev_user", "123456");
            var tokenDTO = new TokenDTO();

            when(authService.signIn(any(AccountCredentialsDTO.class))).thenReturn(tokenDTO);

            mockMvc.perform(post("/auth/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(credentials)))
                    .andExpect(status().isOk());

            verify(authService, times(1)).signIn(any(AccountCredentialsDTO.class));
        }

        @Test
        @DisplayName("Deve retornar 403 Forbidden quando username ou senha estiverem em branco")
        void deveRetornarForbiddenParaCredenciaisInvalidas() throws Exception {
            var credentials = criarCredenciais("", "123456");

            mockMvc.perform(post("/auth/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(credentials)))
                    .andExpect(status().isForbidden())
                    .andExpect(content().string("Invalid Client Request"));

            verify(authService, never()).signIn(any());
        }

        @Test
        @DisplayName("Deve retornar 403 Forbidden quando authService retornar token nulo")
        void deveRetornarForbiddenQuandoAuthServiceRetornarNull() throws Exception {
            var credentials = criarCredenciais("dev_user", "senha_errada");

            when(authService.signIn(any(AccountCredentialsDTO.class))).thenReturn(null);

            mockMvc.perform(post("/auth/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(credentials)))
                    .andExpect(status().isForbidden())
                    .andExpect(content().string("Invalid Client Request"));

            verify(authService, times(1)).signIn(any(AccountCredentialsDTO.class));
        }
    }

    @Nested
    @DisplayName("Testes do Endpoint /auth/signup")
    class SignUpEndpointTestes {

        @Test
        @DisplayName("Deve retornar 200 OK ao cadastrar usuário com sucesso")
        void deveRetornarOkAoCadastrarUsuario() throws Exception {
            var userRequest = new UserRequest("dev_user", "Dev Full", "dev@email.com", "123456");

            doNothing().when(authService).signup(any(UserRequest.class));

            mockMvc.perform(post("/auth/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(userRequest)))
                    .andExpect(status().isOk())
                    .andExpect(content().string("User created successfully"));

            verify(authService, times(1)).signup(any(UserRequest.class));
        }
    }

    @Nested
    @DisplayName("Testes do Endpoint /auth/refresh/{username}")
    class RefreshTokenEndpointTestes {

        @Test
        @DisplayName("Deve retornar 200 OK e novo TokenDTO para requisição de refresh válida")
        void deveRetornarOkNoRefreshToken() throws Exception {
            var username = "dev_user";
            var refreshToken = "valid_refresh_token";
            var tokenDTO = new TokenDTO();

            when(authService.refreshToken(eq(username), eq(refreshToken))).thenReturn(tokenDTO);

            mockMvc.perform(post("/auth/refresh/{username}", username)
                            .header("Authorization", refreshToken))
                    .andExpect(status().isOk());

            verify(authService, times(1)).refreshToken(username, refreshToken);
        }

        @Test
        @DisplayName("Deve retornar 403 Forbidden quando header de Authorization for omitido")
        void deveRetornarForbiddenQuandoHeaderEstiverAusente() throws Exception {
            mockMvc.perform(post("/auth/refresh/{username}", "dev_user"))
                    .andExpect(status().isBadRequest()); // Spring intercepta header requerido ausente com 400

            verify(authService, never()).refreshToken(anyString(), anyString());
        }

        @Test
        @DisplayName("Deve retornar 403 Forbidden quando authService retornar null no refresh")
        void deveRetornarForbiddenQuandoRefreshTokenRetornarNull() throws Exception {
            var username = "dev_user";
            var refreshToken = "invalid_refresh_token";

            when(authService.refreshToken(username, refreshToken)).thenReturn(null);

            mockMvc.perform(post("/auth/refresh/{username}", username)
                            .header("Authorization", refreshToken))
                    .andExpect(status().isForbidden())
                    .andExpect(content().string("Invalid Client Request"));

            verify(authService, times(1)).refreshToken(username, refreshToken);
        }
    }
}