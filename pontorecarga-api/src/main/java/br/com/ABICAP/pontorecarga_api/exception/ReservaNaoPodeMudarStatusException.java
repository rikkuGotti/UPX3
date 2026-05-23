package br.com.ABICAP.pontorecarga_api.exception;

public class ReservaNaoPodeMudarStatusException extends RuntimeException {
    public ReservaNaoPodeMudarStatusException(String message) {
        super(message);
    }
}
