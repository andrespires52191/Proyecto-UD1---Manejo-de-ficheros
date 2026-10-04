package org.example;

import static org.example.EntradaDatos.preguntar;

public class GestionPrestamos {
    public GestionPrestamos() {

        final String MENU_PRESTAMOS = "Acciones:\n" +
                "1) Crear Prestamos\n" +
                "2) Ver Prestamoss\n" +
                "3) Editar Prestamos\n" +
                "4) Eliminar Prestamos\n" +
                "5) Menu Principal";

        // Bucle de selección de acciones de menú.
        int opcion = -1;
        while (opcion != 5) {
            try {
                opcion = Integer.parseInt(preguntar("\n\n" + MENU_PRESTAMOS + "\n\nOpción: "));
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    cearPrestamo();
                    break;
                case 2:
                    verPrestamos();
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

    private void cearPrestamo() {
    }

    private void verPrestamos() {
    }

    private void editarPrestamo() {
    }

    private void eliminarPrestamo() {
    }
}
