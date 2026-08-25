package com.projeto.agendamentos.service;

import com.projeto.agendamentos.dtos.user.UserRequest;
import com.projeto.agendamentos.dtos.user.UserResponse;
import com.projeto.agendamentos.exceptions.NotFoundIdException;
import com.projeto.agendamentos.model.security.User;
import com.projeto.agendamentos.repository.security.UserRepository;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if (user != null) return user;
        else {
            logger.error("Username {} not found", username);
            throw  new UsernameNotFoundException("Username " + username + " not found");
        }
    }

    public UserResponse findByUsername(String username) {
        var user = userRepository.findByUsername(username);
        logger.info("[findByUsername] Buscando usuário por username: {}", username);
        if (user != null) return new UserResponse(user.getId(), user.getUserName(), user.getEmail());
        else {
            throw  new UsernameNotFoundException("Username " + username + " not found");
        }
    }

    public UserResponse findById(Long id) {
        var user = userRepository.findById(id);
        logger.info("[findById] Buscando usuário com id: {}", id);

        if (user.isPresent()) {
            return new UserResponse(user.get().getId(), user.get().getUserName(), user.get().getEmail());
        }
        else {
            logger.error("User with id {} not found", id);
            throw new NotFoundIdException("User with id " + id + " not found");
        }
    }

    public void atualizarUsuario(UserRequest userRequest, Long id) {
        logger.info("[atualizarUsuario] Atualizando usuário com id: {}", id);
        var user = userRepository.findById(id);

        verificarUsuarioExistente(id);
        User usuarioExistente = user.get();
        usuarioExistente.setUserName(userRequest.username());
        usuarioExistente.setEmail(userRequest.email());
        userRepository.save(usuarioExistente);    
       
    }

    public void deletarUsuario(Long id) {
        logger.info("[deletarUsuario] Deletando usuário com id: {}", id);
        validarId(id);
        verificarUsuarioExistente(id);
       userRepository.deleteById(id);
    }



    // Métodos Privados auxiliares


    private void verificarUsuarioExistente(Long id) {
        var user = userRepository.findById(id);
        if (user.isEmpty()) {
            logger.error("Erro na verificação de id -> Usuário com o id: {}.", id);
            throw  new UsernameNotFoundException("Usuário com o id: " + id + " não foi encontrado.");
        }
    }

    private void validarId(Long id) {
        if(id == null || id < 0) 
               throw new IllegalArgumentException("ID de usuário inválido: " + id);
    }
    
}