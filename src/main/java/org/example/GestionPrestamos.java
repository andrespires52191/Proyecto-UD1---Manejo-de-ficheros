package org.example;

import static org.example.Main.scanner;

public class GestionPrestamos {
    public GestionPrestamos() {

        final String MENU_PRESTAMOS = "Acciones:\n" +
                "1) Crear Prestamos\n" +
                "2) Ver Prestamoss\n" +
                "3) Editar Prestamos\n" +
                "4) Eliminar Prestamos\n" +
                "5) Menu Principal";

        int opcion = -1;
        while (opcion != 5) {
            System.out.print("\n\n" + MENU_PRESTAMOS + "\n\nOpción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
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
