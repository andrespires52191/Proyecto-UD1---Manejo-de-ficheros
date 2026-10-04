package org.example.repos;

import com.google.gson.Gson;
import org.example.modelos.ListaPrestamos;
import org.example.modelos.Prestamo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;

public class RepoPrestamos {
    static Gson gson = new Gson();
    static File fichero = new File("ficheros/prestamos.json");
    static ListaPrestamos prestamos = new ListaPrestamos();

    public boolean cargarFichero() {
        if (!fichero.exists()) {
            // nada que cargar
            return true;
        }

        try {
            // abrir archivo
            FileInputStream fis = new FileInputStream(fichero);

            // leer contenido completo del archivo
            String contenido = new String(fis.readAllBytes(), StandardCharsets.UTF_8);

            // cerrar archivo
            fis.close();

            // deserializar y poner en memoria
            prestamos = gson.fromJson(contenido, ListaPrestamos.class);

            // éxito
            return true;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // fallo
        return false;
    }

    public boolean guardarFichero() {
        // convertir lista a json
        String json = gson.toJson(prestamos);
        try {
            // abrir archivo
            FileOutputStream fos = new FileOutputStream(fichero);

            // escribir json
            fos.write(json.getBytes());

            // cerrar archivo
            fos.close();

            // éxito
            return true;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // fallo
        return false;
    }

    public List<Prestamo> getLista() {
        return prestamos.getLista();
    }

    public int getLastId() {
        try {
            return getLista().getLast().getPrestamoId();
        } catch (NoSuchElementException e) {
            return -1;
        }
    }

    public void add(Prestamo prestamo) {
        prestamos.getLista().add(prestamo);
    }

    public void remove(Prestamo prestamo) {
        prestamos.getLista().remove(prestamo);
    }

    public Prestamo buscarPorId(int prestamoId) {
        for (Prestamo p : getLista()) {
            if (p.getPrestamoId() == prestamoId) {
                return p;
            }
        }
        return null;
    }

    public Prestamo buscarPorUsuarioYLibro(int usuarioID, int libroID) {
        for (Prestamo p : getLista()) {
            if (p.getUsuarioId() == usuarioID && p.getLibroId() == libroID) {
                return p;
            }
        }
        return null;
    }
}
