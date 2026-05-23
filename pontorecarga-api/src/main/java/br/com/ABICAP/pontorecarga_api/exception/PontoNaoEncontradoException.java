package br.com.ABICAP.pontorecarga_api.exception;

public class PontoNaoEncontradoException extends RuntimeException {
    public PontoNaoEncontradoException(String message) {
        super(message);
    }
}
