package br.com.ABICAP.pontorecarga_api.config;


import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGen {
    static void main() {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("teste123"));

    }
}
