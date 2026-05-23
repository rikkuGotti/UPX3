package br.com.ABICAP.pontorecarga_api.handler;

import br.com.ABICAP.pontorecarga_api.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErroHandler {


    @ExceptionHandler(UsuarioNaoAutenticadoException.class)
    public ResponseEntity<Map<String, Object>> usuarioNaoAutenticado(UsuarioNaoAutenticadoException ex){
        return ResponseEntity.status(401).body(Map.of(
                "status", 401,
                "mensagem", ex.getMessage()
        ));
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> usuarioNaoEncontrado(UsuarioNaoEncontradoException ex){
        return ResponseEntity.status(404).body(Map.of(
                "status", 404,
                "mensagem", ex.getMessage()
        ));
    }

    @ExceptionHandler(PontoNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> pontoNaoEncontrado(PontoNaoEncontradoException ex){
        return ResponseEntity.status(404).body(Map.of(
                "status", 404,
                "mensagem", ex.getMessage()
        ));
    }

    @ExceptionHandler(ReservaNaoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> reservaNaoEncontrada(ReservaNaoEncontradaException ex){
        return ResponseEntity.status(404).body(Map.of(
                "status", 404,
                "mensagem", ex.getMessage()
        ));
    }

    @ExceptionHandler(UsuarioNaoAutorizadoException.class)
    public ResponseEntity<Map<String, Object>> usuarioNaoAutorizado(UsuarioNaoAutorizadoException ex){
        return ResponseEntity.status(403).body(Map.of(
                "status", 403,
                "mensagem", ex.getMessage()
        ));
    }

    @ExceptionHandler(ReservaNaoAutorizadaException.class)
    public ResponseEntity<Map<String, Object>> reservaNaoAutorizada(ReservaNaoAutorizadaException ex){
        return ResponseEntity.status(403).body(Map.of(
                "status", 403,
                "mensagem", ex.getMessage()
        ));
    }

    @ExceptionHandler(CodigoPontoJaExisteException.class)
    public ResponseEntity<Map<String, Object>> codigoPontoJaExiste(CodigoPontoJaExisteException ex){
        return ResponseEntity.status(409).body(Map.of(
                "status", 409,
                "mensagem", ex.getMessage()
        ));
    }

    @ExceptionHandler(ReservaNaoPodeMudarStatusException.class)
    public ResponseEntity<Map<String, Object>> reservaNaoPodeIniciar(ReservaNaoPodeMudarStatusException ex){
        return ResponseEntity.status(409).body(Map.of(
                "status", 409,
                "mensagem", ex.getMessage()
        ));
    }

    @ExceptionHandler(DadoJaCadastradoException.class)
    public ResponseEntity<Map<String, Object>> dadoJaCadastrado(DadoJaCadastradoException ex){
        return ResponseEntity.status(409).body(Map.of(
                "status", 409,
                "mensagem", ex.getMessage()
        ));
    }

    @ExceptionHandler(RegraReservaException.class)
    public ResponseEntity<Map<String, Object>> regraReserva(RegraReservaException ex){
        return ResponseEntity.status(422).body(Map.of(
                "status", 422,
                "mensagem", ex.getMessage()
        ));
    }






    @ExceptionHandler(MethodArgumentNotValidException .class)
    public ResponseEntity<Map<String, Object>> erroValidacao(MethodArgumentNotValidException ex) {
        String erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest().body(Map.of(
                "status", 400,
                "mensagem", erros
        ));
    }
}
