package com.projeto.agendamentos.controller.security;

import com.projeto.agendamentos.controller.docs.AuthControllerDocs;
import com.projeto.agendamentos.dtos.security.AccountCredentialsDTO;
import com.projeto.agendamentos.dtos.user.UserRequest;
import com.projeto.agendamentos.service.security.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController implements AuthControllerDocs {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody AccountCredentialsDTO credentials) {
        logger.info("Realizando SignIn!");
        if (credentialsIsInvalid(credentials)) return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Invalid Client Request");
        var token = authService.signIn(credentials);
        if (token == null) return ResponseEntity.status((HttpStatus.FORBIDDEN))
                .body("Invalid Client Request");
        return ResponseEntity.ok().body(token);
    }

    @Override
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRequest userRequest) {
        logger.info("Realizando SignUp!");
        authService.signup(userRequest);
        return ResponseEntity.ok().body("User created successfully");
    }

    @Override
    @PostMapping("/refresh/{username}")
    public ResponseEntity<?> refresh(@PathVariable("username") String username,
                                     @RequestHeader("Authorization") String refreshToken) {
        if(parametersAreInvalid(username, refreshToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Invalid Client Request");
        var token = authService.refreshToken(username, refreshToken);
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request");
        return ResponseEntity.ok().body(token);
    }


    // Métodos Privados
    private boolean parametersAreInvalid(String username, String refreshToken) {
        return StringUtils.isEmpty(username) && StringUtils.isEmpty(refreshToken)
                || StringUtils.isEmpty(refreshToken) || StringUtils.isEmpty(username);
    }

    private boolean credentialsIsInvalid(AccountCredentialsDTO credentials) {
        return StringUtils.isBlank(credentials.getUsername()) || StringUtils.isBlank(credentials.getPassword());
    }


}