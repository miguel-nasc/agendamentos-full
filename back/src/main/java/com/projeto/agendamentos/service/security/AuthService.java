package com.projeto.agendamentos.service.security;

import com.projeto.agendamentos.config.SenhaUtils;
import com.projeto.agendamentos.dtos.security.AccountCredentialsDTO;
import com.projeto.agendamentos.dtos.security.TokenDTO;
import com.projeto.agendamentos.dtos.user.UserRequest;
import com.projeto.agendamentos.model.security.User;
import com.projeto.agendamentos.repository.security.UserRepository;
import com.projeto.agendamentos.security.JwtTokenProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtTokenProvider tokenProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    public TokenDTO signIn(AccountCredentialsDTO credentialsDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentialsDTO.getUsername(),
                            credentialsDTO.getPassword()));
        } catch (AuthenticationException e) {
            return null;
        }

        User user = userRepository.findByUsername(credentialsDTO.getUsername());

        if (user == null)
            throw new UsernameNotFoundException("Username " + credentialsDTO.getUsername() + " was not found!");

        return tokenProvider.createAccessToken(
                credentialsDTO.getUsername(),
                user.getRoles());
    }

    
    public void signup(UserRequest userRequest) {

        verificarUsername(userRequest.username());
        verificarEmailExistente(userRequest.email());

        User user = new User();
        user.setUserName(userRequest.username());
        user.setFullName(userRequest.fullname());
        user.setEmail(userRequest.email());
        user.setPassword(SenhaUtils.criptografar(userRequest.password()));
        userRepository.save(user);
    }



    
    public TokenDTO refreshToken(String username, String refreshToken) {
        var user = userRepository.findByUsername(username);
        TokenDTO token;
        if (user != null) token = tokenProvider.refreshToken(refreshToken);
        else throw new UsernameNotFoundException("Username " + username + " not found");
        return token;
    }


    // Métodos auxiliares para verificar se o username e o email já existem no banco de dados
    private void verificarUsername(String username) {
        if (userRepository.findByUsername(username) != null) {
            logger.error("Username " + username + " already exists");
            throw new IllegalArgumentException("Username " + username + " already exists");
        }
    }

    private void verificarEmailExistente(String email) {
        if (userRepository.findByEmail(email) != null) {
            logger.error("Email " + email + " already exists");
            throw new IllegalArgumentException("Email " + email + " already exists");
        }
    }

}