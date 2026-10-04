package org.example;

import com.google.gson.Gson;
import org.example.modelos.ListaPrestamos;
import org.example.modelos.Prestamo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.example.EntradaDatos.preguntar;

public class GestionPrestamos {
    static Gson gson = new Gson();
    static File fichero = new File("ficheros/prestamos.json");
    static ListaPrestamos prestamos = new ListaPrestamos();

    public GestionPrestamos() {
        final String MENU_PRESTAMOS = "Acciones:\n" +
                "1) Ver Prestamos\n" +
                "2) Crear Prestamos\n" +
                "3) Editar Prestamos\n" +
                "4) Eliminar Prestamos\n" +
                "5) Menu Principal";


        if (!cargarFicheroPrestamos()) {
            System.out.println("Error: Fallo al cargar préstamos.");
            return;
        }

        // Bucle de selección de acciones de menú.
        int opcion = -1;
        while (opcion != 5) {
            try {
                opcion = Integer.parseInt(preguntar(MENU_PRESTAMOS + "\n\nOpción: "));
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    verPrestamos();
                    break;
                case 2:
                    cearPrestamo();
                    break;
                case 3:
                    editarPrestamo();
                    break;
                case 4:
                    eliminarPrestamo();
                    break;
                case 5:
                    // condición de salida del bucle
                    break;
                default:
                    System.out.println("Error. Intenta de nuevo.");
            }
        }
    }

    private boolean cargarFicheroPrestamos() {
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

    private boolean guardarFicheroPrestamos() {
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

    private Prestamo buscarPrestamoPorId(int prestamoId) {
        for (Prestamo p : prestamos.getLista()) {
            if (p.getPrestamoId() == prestamoId) {
                return p;
            }
        }
        return null;
    }

    private Prestamo buscarPrestamoPorUsuarioYLibro(int usuarioID, int libroID) {
        for (Prestamo p : prestamos.getLista()) {
            if (p.getUsuarioId() == usuarioID && p.getLibroId() == libroID) {
                return p;
            }
        }
        return null;
    }

    private void verPrestamos() {
        System.out.println("Resultados:");
        for (Prestamo p : prestamos.getLista()) {
            System.out.println("Préstamo#" + p.getPrestamoId() + " - " +
                    "Usuario#" + p.getUsuarioId() + " - " +
                    "Libro#" + p.getLibroId() + " - " +
                    "(" + p.getFechaIni() + " - " + p.getFechaFin() + ")");
        }
        System.out.println();
    }

    private void cearPrestamo() {
        // pedir datos para crear
        int usuarioId = 0;
        int libroID = 0;
        try {
            usuarioId = Integer.parseInt(EntradaDatos.preguntar("ID de Usuario: "));
            libroID = Integer.parseInt(EntradaDatos.preguntar("ID de libro: "));
        } catch (NumberFormatException e) {
            System.out.println("Error al recoger ID.");
            return;
        }
        String fechaIni = preguntar("Fecha inicio: ");
        String fechaFin = preguntar("fecha fin: ");
        Prestamo prestamoNuevo = new Prestamo(usuarioId, libroID, fechaIni, fechaFin);

        // buscar prestamos repetidos
        Prestamo prestamoExistente = buscarPrestamoPorUsuarioYLibro(usuarioId, libroID);
        if (prestamoExistente != null) {
            System.out.println("El préstamo ya existe.");
            return;
        }

        // añadir a memoria
        prestamoNuevo.setPrestamoId(prestamos.getLista().getLast().getPrestamoId() + 1);
        prestamos.add(prestamoNuevo);

        // volcar memoria a archivo
        if (guardarFicheroPrestamos()) {
            System.out.println("Préstamo guardado correctamente.");
        }
    }

    private void editarPrestamo() {
        // pedir datos de búsqueda
        int prestamoId = -1;
        try {
            prestamoId = Integer.parseInt(EntradaDatos.preguntar("ID de préstamo: "));
        } catch (NumberFormatException e) {
            System.out.println("Error al recoger ID.");
            return;
        }

        // buscar libro en memoria
        Prestamo prestamo = buscarPrestamoPorId(prestamoId);
        if (prestamo == null) {
            System.out.println("El préstamo no existe.");
            return;
        }

        // pedir nuevos valores
        System.out.println("Introduce los datos actualizados.");
        String fechaIni = preguntar("Fecha inicio: ");
        String fechaFin = preguntar("Fecha final: ");

        // actualizar en memoria
        prestamo.setFechaIni(fechaIni);
        prestamo.setFechaFin(fechaFin);

        // volcar a archivo
        if (guardarFicheroPrestamos()) {
            System.out.println("Préstamo editado correctamente.");
        }
    }

    private void eliminarPrestamo() {
        // pedir datos de búsqueda
        int prestamoId = -1;
        try {
            prestamoId = Integer.parseInt(EntradaDatos.preguntar("ID de préstamo: "));
        } catch (NumberFormatException e) {
            System.out.println("Error al recoger ID.");
            return;
        }

        // buscar libro en memoria
        Prestamo prestamo = buscarPrestamoPorId(prestamoId);
        if (prestamo == null) {
            System.out.println("El préstamo no existe.");
            return;
        }

        // eliminar en memoria
        prestamos.remove(prestamo);

        // volcar a archivo
        if (guardarFicheroPrestamos()) {
            System.out.println("Préstamo editado correctamente.");
        }
    }
}
