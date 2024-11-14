package com.montonurb.screenmatch_backend.enums;

public enum Categoria {
    ACAO("Action", "Ação"),
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comédia"),
    DRAMA("Drama", "Drama"),
    CRIME("Crime", "Crime"),
    ANIMACAO("Animation", "Animação");

    private String categoriaOmdb;
    private String categoriaPortugues;

    Categoria(String categoriaOmdb, String categoriaPortugues) {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPortugues = categoriaPortugues;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a String fornecida!");
    }

    public static Categoria fromPortugues(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaPortugues.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a String fornecida!");
    }

    public String getCategoriaOmdb() {
        return categoriaOmdb;
    }

    public void setCategoriaOmdb(String categoriaOmdb) {
        this.categoriaOmdb = categoriaOmdb;
    }

    public String getCategoriaPortugues() {
        return categoriaPortugues;
    }

    public void setCategoriaPortugues(String categoriaPortugues) {
        this.categoriaPortugues = categoriaPortugues;
    }
}
