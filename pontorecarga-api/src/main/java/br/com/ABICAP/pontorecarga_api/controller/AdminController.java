package br.com.ABICAP.pontorecarga_api.controller;

import br.com.ABICAP.pontorecarga_api.dto.DTOPontoRecargaRequest;
import br.com.ABICAP.pontorecarga_api.model.PontoRecarga;
import br.com.ABICAP.pontorecarga_api.model.TipoUsuario;
import br.com.ABICAP.pontorecarga_api.model.Usuario;
import br.com.ABICAP.pontorecarga_api.repository.UsuarioRepository;
import br.com.ABICAP.pontorecarga_api.service.AdminService;
import br.com.ABICAP.pontorecarga_api.service.PontoRecargaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private UsuarioRepository usuarioRepository;

    private AdminService adminService;

    private PontoRecargaService pontoRecargaService;

    @Autowired
    public AdminController(UsuarioRepository usuarioRepository, AdminService adminService, PontoRecargaService pontoRecargaService) {
        this.usuarioRepository = usuarioRepository;
        this.adminService = adminService;
        this.pontoRecargaService = pontoRecargaService;
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

    @PostMapping("/criarponto")
    public ResponseEntity<?> criarPonto(@RequestBody DTOPontoRecargaRequest request, HttpSession session){

        adminService.validarAdmin(session);

        PontoRecarga ponto = pontoRecargaService.cadastrarPontoRecarga(request);


        return ResponseEntity.status(201).body(ponto);
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
