package br.com.ABICAP.pontorecarga_api.controller;


import br.com.ABICAP.pontorecarga_api.model.Usuario;
import br.com.ABICAP.pontorecarga_api.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    public String login(@RequestParam String usuario, @RequestParam String senha, HttpSession session){

        if(usuarioService.autenticar(usuario, senha)){
            session.setAttribute("USUARIO_LOGADO", usuario);
            return "Login ok! O cookie de sessão foi gerado.";
        }

        return "Erro!! Usuario ou senha invalidos";
    }
}



