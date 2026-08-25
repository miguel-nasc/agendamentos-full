package com.projeto.agendamentos.controller;


import com.projeto.agendamentos.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projeto.agendamentos.dtos.user.UserRequest;
import com.projeto.agendamentos.dtos.user.UserResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable(name = "id") Long id) {
        return userService.findById(id);
    }

    @GetMapping("/username/{username}")
    public UserResponse findByUsername(@PathVariable(name = "username") String username) {
        return userService.findByUsername(username);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarUsuario(@RequestBody UserRequest userRequest, @PathVariable Long id) {
        userService.atualizarUsuario(userRequest, id);
        return ResponseEntity.ok("Usuário atualizado com sucesso");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPorId(@PathVariable Long id) {
        userService.deletarUsuario(id);
        return ResponseEntity.ok("Usuário deletado com sucesso");
    }


    
    
}
