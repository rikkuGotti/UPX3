package br.com.ABICAP.pontorecarga_api.exception;

public class ReservaNaoAutorizadaException extends RuntimeException {
    public ReservaNaoAutorizadaException(String message) {
        super(message);
    }
}
