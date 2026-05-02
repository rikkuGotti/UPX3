package br.com.ABICAP.pontorecarga_api.controller;

import br.com.ABICAP.pontorecarga_api.dto.DTOAtualizarDadosRequest;
import br.com.ABICAP.pontorecarga_api.dto.DTOCarroUsuario;
import br.com.ABICAP.pontorecarga_api.dto.DTORespostaUsuario;
import br.com.ABICAP.pontorecarga_api.model.Usuario;
import br.com.ABICAP.pontorecarga_api.repository.UsuarioRepository;
import br.com.ABICAP.pontorecarga_api.service.AdminService;
import br.com.ABICAP.pontorecarga_api.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/meu-perfil/alterar-dados/{id}")
    public ResponseEntity<?> alterarDados(@RequestBody DTOAtualizarDadosRequest dados, HttpSession session){
        usuarioService.validarUsuario(session);



        return ResponseEntity.status(201).body("ok");
    }

    @GetMapping("/meu-perfil")
    public ResponseEntity<?> getPerfil(HttpSession session){
        String usuarioEstaLogado = (String) session.getAttribute("USUARIO_LOGADO");


        if(usuarioEstaLogado == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nao Logado");
        }

        Usuario usuario = usuarioRepository.findByUsuario(usuarioEstaLogado)
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

        DTORespostaUsuario respostaUsuario = new DTORespostaUsuario();
        respostaUsuario.setId(usuario.getId());
        respostaUsuario.setUsuario(usuario.getUsuario());
        respostaUsuario.setEmail(usuario.getEmail());
        DTOCarroUsuario respostaCarroUsuario = new DTOCarroUsuario();
        respostaCarroUsuario.setId(usuario.getCarroUsuario().getId());
        respostaCarroUsuario.setModelo(usuario.getCarroUsuario().getModel());

        respostaUsuario.setCarroUsuario(respostaCarroUsuario);

        return ResponseEntity.ok(respostaUsuario);
    }
}
