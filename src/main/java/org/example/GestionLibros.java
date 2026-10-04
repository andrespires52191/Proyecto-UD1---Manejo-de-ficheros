package org.example;

import static org.example.EntradaDatos.preguntar;

public class GestionLibros {
    public GestionLibros() {

        final String MENU_LIBROS = "Acciones:\n" +
                "1) Crear Libro\n" +
                "2) Ver Libros\n" +
                "3) Editar Libro\n" +
                "4) Eliminar Libro\n" +
                "5) Menu Principal";

        // Bucle de selección de acciones de menú.
        int opcion = -1;
        while (opcion != 5) {
            try {
                opcion = Integer.parseInt(preguntar("\n\n" + MENU_LIBROS + "\n\nOpción: "));
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    cearLibro();
                    break;
                case 2:
                    verLibros();
                    break;
                case 3:
                    editarLibro();
                    break;
                case 4:
                    eliminarLibro();
                    break;
                case 5:
                    // condición de salida del bucle
                    break;
                default:
                    System.out.println("Error. Intenta de nuevo.");
            }
        }
    }

    private void cearLibro() {
    }

    private void verLibros() {
    }

    private void editarLibro() {
    }

    private void eliminarLibro() {
    }
}
