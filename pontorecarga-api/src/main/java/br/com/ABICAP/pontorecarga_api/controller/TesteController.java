package br.com.ABICAP.pontorecarga_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}