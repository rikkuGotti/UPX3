package br.com.ABICAP.pontorecarga_api.controller;

import br.com.ABICAP.pontorecarga_api.model.TipoUsuario;
import br.com.ABICAP.pontorecarga_api.model.Usuario;
import br.com.ABICAP.pontorecarga_api.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    UsuarioRepository usuarioRepository;

    public AdminController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/teste")
    private ResponseEntity<?> teste(HttpSession session){
        String adminEstaLogado = (String) session.getAttribute("USUARIO_LOGADO");
        Usuario usuario = usuarioRepository.findByUsuario(adminEstaLogado).orElseThrow(null);



        if(usuario.getTipoUsuario().equals(TipoUsuario.USER) || adminEstaLogado == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
        }


        return ResponseEntity.ok("Sucesso");
    }


//    @PostMapping("/cadastrar-novo-admin")
//    private ResponseEntity<?> criarAdmin(@RequestBody Usuario adminUsuario, HttpSession session){
//        String adminEstaLogado = (String) session.getAttribute("USUARIO_LOGADO");
//
//        if(adminEstaLogado == null){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nao Logado");
//        }
//    }


}
