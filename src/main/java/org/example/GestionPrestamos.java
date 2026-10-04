package org.example;

import org.example.modelos.Prestamo;
import org.example.repos.RepoLibros;
import org.example.repos.RepoPrestamos;
import org.example.repos.RepoUsuarios;

import static org.example.EntradaDatos.preguntar;

public class GestionPrestamos {
    RepoPrestamos repoPrestamos;
    RepoUsuarios repoUsuarios;
    RepoLibros repoLibros;

    public GestionPrestamos() {
    }

    public GestionPrestamos(RepoPrestamos repoPrestamos, RepoUsuarios repoUsuarios, RepoLibros repoLibros) {
        this.repoPrestamos = repoPrestamos;
        this.repoUsuarios = repoUsuarios;
        this.repoLibros = repoLibros;
    }

    public void mostrarMenu() {
        final String MENU_PRESTAMOS = "Acciones:\n" +
                "1) Ver Prestamos\n" +
                "2) Crear Prestamos\n" +
                "3) Editar Prestamos\n" +
                "4) Eliminar Prestamos\n" +
                "5) Menu Principal";


        if (!repoPrestamos.cargarFichero()) {
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

    private void verPrestamos() {
        System.out.println("Resultados:");
        for (Prestamo p : repoPrestamos.getLista()) {
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
        Prestamo prestamoExistente = repoPrestamos.buscarPorUsuarioYLibro(usuarioId, libroID);
        if (prestamoExistente != null) {
            System.out.println("El préstamo ya existe.");
            return;
        }

        // añadir a memoria
        prestamoNuevo.setPrestamoId(repoPrestamos.getLastId() + 1);
        repoPrestamos.add(prestamoNuevo);

        // volcar memoria a archivo
        if (repoPrestamos.guardarFichero()) {
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
        Prestamo prestamo = repoPrestamos.buscarPorId(prestamoId);
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
        if (repoPrestamos.guardarFichero()) {
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
        Prestamo prestamo = repoPrestamos.buscarPorId(prestamoId);
        if (prestamo == null) {
            System.out.println("El préstamo no existe.");
            return;
        }

        // eliminar en memoria
        repoPrestamos.remove(prestamo);

        // volcar a archivo
        if (repoPrestamos.guardarFichero()) {
            System.out.println("Préstamo editado correctamente.");
        }
    }
}
