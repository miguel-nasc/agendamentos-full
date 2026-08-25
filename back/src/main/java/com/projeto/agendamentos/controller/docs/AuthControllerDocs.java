package com.projeto.agendamentos.controller.docs;

import com.projeto.agendamentos.dtos.security.AccountCredentialsDTO;
import com.projeto.agendamentos.dtos.user.UserRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface AuthControllerDocs {

    @Operation(
            summary = "Realiza login",
            description = "Autentica um usuário e retorna um token JWT.",
            tags = {"Auth"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login realizado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Credenciais inválidas"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno do servidor"
                    )
            }
    )
    @SecurityRequirements
    ResponseEntity<?> signin(
            @RequestBody AccountCredentialsDTO credentials
    );


    @Operation(
            summary = "Atualiza o token",
            description = "Gera um novo token de acesso utilizando o refresh token.",
            tags = {"Auth"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Token atualizado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Refresh token inválido"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno do servidor"
                    )
            }
    )
    @SecurityRequirements
    ResponseEntity<?> refresh(
            @Parameter(
                    description = "Nome do usuário",
                    required = true
            )
            @PathVariable("username") String username,

            @Parameter(
                    description = "Refresh token",
                    required = true
            )
            @RequestHeader("Authorization") String refreshToken
    );

    @Operation(
            summary = "Cria um novo usuário",
            description = "Registra um novo usuário no sistema.",
            tags = {"Auth"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuário criado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Requisição inválida"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno do servidor"
                    )
            }
    )
    public ResponseEntity<?> signup(@RequestBody UserRequest userRequest);
}