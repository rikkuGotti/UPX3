package br.com.ABICAP.pontorecarga_api.controller;

import br.com.ABICAP.pontorecarga_api.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

//    @PostMapping("/cadastrar-novo-admin")
//    private ResponseEntity<?> criarAdmin(@RequestBody Usuario adminUsuario, HttpSession session){
//        String adminEstaLogado = (String) session.getAttribute("USUARIO_LOGADO");
//
//        if(adminEstaLogado == null){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nao Logado");
//        }
//    }


}
