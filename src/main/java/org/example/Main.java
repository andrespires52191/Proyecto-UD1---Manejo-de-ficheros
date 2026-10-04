package org.example;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    static void main() {
        System.out.println("Gestión de Biblioteca\n" +
                "---------------------");

        menuPrincipal();

        System.out.println("Programa terminado.");
    }

    private static void menuPrincipal() {
        final String MENU_PRINCIPAL = "Acciones:\n" +
                "1) Gestionar Usuarios\n" +
                "2) Gestionar Libros\n" +
                "3) Gestionar Préstamos\n" +
                "4) Salir";

        // Bucle de selección de acciones de menú.
        int opcion = -1;
        while (opcion != 4) {
            System.out.print("\n" + MENU_PRINCIPAL + "\n\nOpción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    new GestionUsuarios();
                    break;
                case 2:
                    new GestionLibros();
                    break;
                case 3:
                    new GestionPrestamos();
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Error. Intenta de nuevo.");
            }
        }
    }
}
