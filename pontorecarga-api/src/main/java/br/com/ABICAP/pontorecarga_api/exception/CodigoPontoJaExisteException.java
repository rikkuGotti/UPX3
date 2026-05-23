package br.com.ABICAP.pontorecarga_api.exception;

public class CodigoPontoJaExisteException extends RuntimeException {
    public CodigoPontoJaExisteException(String message) {
        super(message);
    }
}
