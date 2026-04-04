package br.com.ABICAP.pontorecarga_api.controller;

import br.com.ABICAP.pontorecarga_api.model.Usuario;
import br.com.ABICAP.pontorecarga_api.repository.UsuarioRepository;
import br.com.ABICAP.pontorecarga_api.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private UsuarioService usuarioService;

    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/meu-perfil")
    public ResponseEntity<?> getPerfil(HttpSession session){
        String usuarioEstaLogado = (String) session.getAttribute("USUARIO_LOGADO");


        if(usuarioEstaLogado == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nao Logado");
        }

        Usuario usuario = usuarioRepository.findByUsuario(usuarioEstaLogado)
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));


        return ResponseEntity.ok(usuario);
    }
}
