package org.example;

import static org.example.EntradaDatos.preguntar;

public class GestionUsuarios {
    public GestionUsuarios() {

        final String MENU_USUARIO = "Acciones:\n" +
                "1) Crear Usuario\n" +
                "2) Ver Usuarios\n" +
                "3) Editar Usuario\n" +
                "4) Eliminar Usuario\n" +
                "5) Menu Principal";

        // Bucle de selección de acciones de menú.
        int opcion = -1;
        while (opcion != 5) {
            try {
                opcion = Integer.parseInt(preguntar("\n\n" + MENU_USUARIO + "\n\nOpción: "));
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    cearUsuario();
                    break;
                case 2:
                    verUsuarios();
                    break;
                case 3:
                    editarUsuario();
                    break;
                case 4:
                    eliminarUsuario();
                    break;
                case 5:
                    // condición de salida del bucle
                    break;
                default:
                    System.out.println("Error. Intenta de nuevo.");
            }
        }
    }

    private void cearUsuario() {
    }

    private void verUsuarios() {
    }

    private void editarUsuario() {
    }

    private void eliminarUsuario() {
    }
}
