package br.com.ABICAP.pontorecarga_api.controller;


import br.com.ABICAP.pontorecarga_api.dto.DTOCriarContaRequest;
import br.com.ABICAP.pontorecarga_api.dto.DTOLoginUsuarioRequest;
import br.com.ABICAP.pontorecarga_api.model.Usuario;
import br.com.ABICAP.pontorecarga_api.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> cadastroUsuario(@RequestBody @Valid DTOCriarContaRequest request, HttpSession session){

        Usuario usuario = usuarioService.cadastroUsuario(request);

        String user = request.getUsuario();

        session.setAttribute("USUARIO_LOGADO", user);


        return ResponseEntity.status(201).body("Conta criada com sucesso");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid DTOLoginUsuarioRequest login, HttpSession session){

        String usuario = login.getLogin();
        String senha = login.getSenha();

        boolean autenticado = usuarioService.autenticar(usuario, senha);

        if(autenticado){
            session.setAttribute("USUARIO_LOGADO", usuario);
            return ResponseEntity.ok("Login realizado");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro, algo esta invalido");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout realizado com sucesso");
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



