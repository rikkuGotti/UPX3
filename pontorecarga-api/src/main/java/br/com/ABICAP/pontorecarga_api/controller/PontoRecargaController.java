package br.com.ABICAP.pontorecarga_api.controller;

import br.com.ABICAP.pontorecarga_api.model.PontoRecarga;
import br.com.ABICAP.pontorecarga_api.repository.PontoRecargaRepository;
import br.com.ABICAP.pontorecarga_api.service.AdminService;
import br.com.ABICAP.pontorecarga_api.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recarga")
public class PontoRecargaController {

    private PontoRecargaRepository pontoRecargaRepository;

    private UsuarioService usuarioService;

    private AdminService adminService;

    @Autowired
    public PontoRecargaController(PontoRecargaRepository pontoRecargaRepository, UsuarioService usuarioService, AdminService adminService) {
        this.pontoRecargaRepository = pontoRecargaRepository;
        this.usuarioService = usuarioService;
        this.adminService = adminService;
    }

    @GetMapping("/pontos")
    public ResponseEntity<?> listarPontos(){
        List<PontoRecarga> pontos = pontoRecargaRepository.findAll();

        return ResponseEntity.status(200).body(pontos);
    }
}
