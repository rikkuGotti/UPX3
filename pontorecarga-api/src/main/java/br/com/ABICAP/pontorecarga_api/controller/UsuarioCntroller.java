package br.com.ABICAP.pontorecarga_api.controller;


import br.com.ABICAP.pontorecarga_api.model.Usuario;
import br.com.ABICAP.pontorecarga_api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioCntroller {

    private UsuarioService usuarioService;

    @Autowired
    public UsuarioCntroller(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/auth")
    public Usuario cadastroUsuario(@RequestBody Usuario usuario){
        return usuarioService.cadastroUsuario(usuario);
    }
}
