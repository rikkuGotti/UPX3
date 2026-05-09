package br.com.ABICAP.pontorecarga_api.controller;

import br.com.ABICAP.pontorecarga_api.dto.DTOAtualizarDadosRequest;
import br.com.ABICAP.pontorecarga_api.dto.DTOCarroUsuarioResponse;
import br.com.ABICAP.pontorecarga_api.dto.DTOTrocarCarroRequest;
import br.com.ABICAP.pontorecarga_api.dto.DTOUsuarioResponse;
import br.com.ABICAP.pontorecarga_api.model.Usuario;
import br.com.ABICAP.pontorecarga_api.repository.UsuarioRepository;
import br.com.ABICAP.pontorecarga_api.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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

    @PatchMapping("/meu-perfil/alterar-dados")
    public ResponseEntity<?> alterarDados(@RequestBody @Valid DTOAtualizarDadosRequest dados, HttpSession session){
        Usuario usuario = usuarioService.validarUsuario(session);

        usuarioService.alterarDados(dados, usuario);

        return ResponseEntity.status(201).body("Alteracao feita com sucesso");
    }

    @PutMapping("/meu-perfil/alterar-dados-carro")
    public ResponseEntity<?> alterarCarro(@RequestBody @Valid DTOTrocarCarroRequest dados, HttpSession session){
        Usuario usuario = usuarioService.validarUsuario(session);

        usuarioService.alterarCarro(dados, usuario);

        return ResponseEntity.status(201).body("Carro atualizado com sucesso");
    }

    @GetMapping("/meu-perfil")
    public ResponseEntity<?> getPerfil(HttpSession session){
        String usuarioEstaLogado = (String) session.getAttribute("USUARIO_LOGADO");


        if(usuarioEstaLogado == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nao Logado");
        }

        Usuario usuario = usuarioRepository.findByUsuario(usuarioEstaLogado)
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

        DTOUsuarioResponse respostaUsuario = new DTOUsuarioResponse();
        respostaUsuario.setId(usuario.getId());
        respostaUsuario.setUsuario(usuario.getUsuario());
        respostaUsuario.setEmail(usuario.getEmail());
        DTOCarroUsuarioResponse respostaCarroUsuario = new DTOCarroUsuarioResponse();
        respostaCarroUsuario.setId(usuario.getCarroUsuario().getId());
        respostaCarroUsuario.setModelo(usuario.getCarroUsuario().getModel());

        respostaUsuario.setCarroUsuario(respostaCarroUsuario);

        return ResponseEntity.ok(respostaUsuario);
    }
}
