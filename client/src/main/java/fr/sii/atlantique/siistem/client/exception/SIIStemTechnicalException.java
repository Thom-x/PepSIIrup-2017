package fr.sii.atlantique.siistem.client.exception;

public class SIIStemTechnicalException extends RuntimeException {

    public SIIStemTechnicalException(String msg) {
        super(msg);
    }

    public SIIStemTechnicalException(String msg, Throwable e) {
        super(msg, e);
    }
}
