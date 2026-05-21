package br.com.ABICAP.pontorecarga_api.controller;

import br.com.ABICAP.pontorecarga_api.dto.DTODadosRelatorioPDF;
import br.com.ABICAP.pontorecarga_api.service.AdminService;
import br.com.ABICAP.pontorecarga_api.service.RelatoriosPDFService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PdfController {

    @Autowired
    RelatoriosPDFService relatoriosPDFService;

    @Autowired
    AdminService adminService;

    @GetMapping("relatorioPDF")
    public ResponseEntity<byte[]> gerarPDF(HttpSession session){
        adminService.validarAdmin(session);

        DTODadosRelatorioPDF dadosRelatorioPDF = new DTODadosRelatorioPDF();

        byte[] pdfBytes = relatoriosPDFService.gerarRelatorioPDF(dadosRelatorioPDF);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "ChargeNow.pdf");

        return  ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}
