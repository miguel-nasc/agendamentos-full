package com.projeto.agendamentos.config;

import org.mindrot.jbcrypt.BCrypt;

public class SenhaUtils {

    // 1. Gera o Hash da senha (Usado no CADASTRO)
    public static String criptografar(String senhaPura) {
        return BCrypt.hashpw(senhaPura, BCrypt.gensalt());
    }

    // 2. Verifica se a senha bate com o Hash (Usado no LOGIN)
    public static boolean verificar(String senhaPura, String hashDoBanco) {
        // O BCrypt descobre o salt original direto de dentro do hashDoBanco
        return BCrypt.checkpw(senhaPura, hashDoBanco);
    }
}