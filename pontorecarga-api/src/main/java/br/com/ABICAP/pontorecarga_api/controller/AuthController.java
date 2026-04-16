package br.com.ABICAP.pontorecarga_api.controller;


import br.com.ABICAP.pontorecarga_api.model.Usuario;
import br.com.ABICAP.pontorecarga_api.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private UsuarioService usuarioService;

    @Autowired
    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastro")
    public Usuario cadastroUsuario(@RequestBody Usuario usuario){

        return usuarioService.cadastroUsuario(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String usuario, @RequestParam String senha, HttpSession session){

        boolean autenticado = usuarioService.autenticar(usuario, senha);

        if(autenticado){
            session.setAttribute("USUARIO_LOGADO", usuario);
            return ResponseEntity.ok("Login realizado");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro, algo esta invalido");
    }




//
//    @PostMapping("/teste-raw")
//    public ResponseEntity<?> testeRaw(@RequestBody String body) {
//        System.out.println("===== RAW BODY =====");
//        System.out.println(body);
//        System.out.println("===================");
//
//        return ResponseEntity.ok("Recebido: " + body);
//    }

}



