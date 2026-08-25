package com.projeto.agendamentos.controller;


import com.projeto.agendamentos.controller.docs.AgendamentoControllerDocs;
import com.projeto.agendamentos.dtos.agendamento.AgendamentoRequest;
import com.projeto.agendamentos.dtos.agendamento.AgendamentoResponse;
import com.projeto.agendamentos.service.AgendamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController implements AgendamentoControllerDocs {

    private final Logger logger = LoggerFactory.getLogger(AgendamentoController.class);
    private final AgendamentoService service;

    public AgendamentoController(AgendamentoService service) {
        this.service = service;
    }

    @Override
    @PostMapping("/realizar")
    public ResponseEntity<?> realizarAgendamento(@RequestBody AgendamentoRequest agendamentoRequest) {

        String titulo = agendamentoRequest.titulo();
        String reservado_por = agendamentoRequest.reservado_por();
        Long salaId = agendamentoRequest.salaId();
        String data = agendamentoRequest.data();
        String hora_inicio = agendamentoRequest.horaInicio();
        String hora_fim = agendamentoRequest.horaFim();
        logger.info("Salvando Agendamento com as informações do AgendamentoRequest!");
        service.salvarAgendamento(titulo, reservado_por, salaId, data, hora_inicio, hora_fim);
        return ResponseEntity.ok("Agendamento realizado com sucesso!");
    }

    @GetMapping
public ResponseEntity<CollectionModel<EntityModel<AgendamentoResponse>>> listarTodos() {
    // 1. O tipo correto da lista é List<EntityModel<AgendamentoResponse>>
    List<EntityModel<AgendamentoResponse>> agendamentos = service.listarTodosAgendamentos()
            .stream()
            .map(response -> EntityModel.of(
                    response,
                    linkTo(methodOn(AgendamentoController.class).buscarPorId(Long.parseLong(response.id()))).withSelfRel()
            ))
            .toList();

    CollectionModel<EntityModel<AgendamentoResponse>> collectionModel = CollectionModel.of(
            agendamentos,
            linkTo(methodOn(AgendamentoController.class).listarTodos()).withSelfRel()
    );

    return ResponseEntity.ok(collectionModel);
}

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<AgendamentoResponse>> buscarPorId(@PathVariable(name = "id")
                                                                            Long id) {
        var agendamento = service.buscarAgendamentoPorId(id);
        return ResponseEntity.ok(
                EntityModel.of(
                        agendamento,
                        linkTo(methodOn(AgendamentoController.class).buscarPorId(id)).withSelfRel(),
                        linkTo(methodOn(AgendamentoController.class).listarTodos()).withRel("listarTodos")
                )
        );
    }


}
