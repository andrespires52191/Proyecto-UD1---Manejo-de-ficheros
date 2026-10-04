package org.example;

import java.io.File;

import static org.example.EntradaDatos.preguntar;

public class Main {
    static void main() {
        System.out.println("Gestión de Biblioteca\n" +
                "---------------------");

        // Asegurarse de que la carpeta ficheros esta creada para poder usarla luego.
        File carpeta = new File("ficheros");
        carpeta.mkdirs();
        // Queremos que exista, da igual si estaba creada
        // o ha sido creada en la instrucción de arriba.
        if (!carpeta.exists()) {
            System.out.println("Error preparando inicio.");
            return;
        }

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
            try {
                opcion = Integer.parseInt(preguntar("\n" + MENU_PRINCIPAL + "\n\nOpción: "));
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
