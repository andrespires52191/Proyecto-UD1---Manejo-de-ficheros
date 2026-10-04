package org.example.modelos;

import java.util.ArrayList;
import java.util.List;

public class ListaPrestamos {
    List<Prestamo> lista = new ArrayList<>();

    public ListaPrestamos() {
    }

    public List<Prestamo> getLista() {
        return lista;
    }

    public boolean add(Prestamo prestamo) {
        return lista.add(prestamo);
    }

    public boolean remove(Prestamo prestamo) {
        return lista.remove(prestamo);
    }
}
