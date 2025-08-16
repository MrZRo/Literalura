package com.aluradesafio.bibliura.model;

public enum Idioma {
    INGLES("en", "Inglés"),
    ESPAÑOL("es", "Español"),
    PORTUGUES("pt", "Portugués"),
    FRANCES("fr", "Francés"),
    ALEMAN("de", "Alemán");

    private final String codigo;
    private final String nombreCompleto;

    Idioma(String codigo, String nombreCompleto) {
        this.codigo = codigo;
        this.nombreCompleto = nombreCompleto;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public static Idioma fromCodigo(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.codigo.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Idioma no encontrado por código");
    }
    public static String getNombreCompletoFromCodigo(String codigo) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.codigo.equalsIgnoreCase(codigo)) {
                return idioma.nombreCompleto;
            }
        }
        return "Desconocido";
    }

    public static Idioma fromNombre(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.nombreCompleto.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Idioma no encontrado por nombre");
    }
}