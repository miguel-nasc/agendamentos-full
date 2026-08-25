package com.projeto.agendamentos.controller.docs;

import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import com.projeto.agendamentos.dtos.agendamento.AgendamentoRequest;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

public interface AgendamentoControllerDocs {


    @Operation(
            summary = "Realiza um agendamento",
            description = "Permite que um usuário realize um agendamento.",
            tags = {"Agendamento"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Agendamento realizado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno do servidor"
                    )
                    }
            )
    ResponseEntity<?> realizarAgendamento(@RequestBody AgendamentoRequest agendamentoRequest);

    
}
