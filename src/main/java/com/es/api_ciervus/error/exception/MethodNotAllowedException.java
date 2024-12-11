package com.es.api_ciervus.error.exception;

public class MethodNotAllowedException extends RuntimeException {
    private static final String DESCRIPCION = "Method Not Allowed (405)";

    public MethodNotAllowedException(String mensaje) {
        super(DESCRIPCION + ": " + mensaje);
    }
}
