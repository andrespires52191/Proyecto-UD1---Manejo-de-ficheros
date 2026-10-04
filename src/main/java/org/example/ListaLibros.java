package org.example;

import org.example.modelos.Libro;

import java.util.ArrayList;
import java.util.List;

public class ListaLibros {
    private final List<Libro> lista = new ArrayList<>();

    public ListaLibros() {
    }

    public List<Libro> getLista() {
        return lista;
    }

    public boolean add(Libro libro) {
        return lista.add(libro);
    }

    public boolean remove(Libro libro) {
        return lista.remove(libro);
    }
}
