package com.example.demo.exceptions;

public class RecursoNoEncontrado extends Exception {

    @Override
    public String getMessage() {
        return "El recurso solicitado no existe en la base de datos";
    }
}
