package org.example.modelos;

import java.util.ArrayList;
import java.util.List;

public class ListaLibros {
    private final List<Libro> lista = new ArrayList<>();

    public ListaLibros() {
    }

    public List<Libro> getLista() {
        return lista;
    }
}
