package org.example;

import org.example.modelos.Usuario;
import org.example.repos.RepoUsuarios;

import static org.example.EntradaDatos.preguntar;

public class GestionUsuarios {
    private final RepoUsuarios repoUsuarios;

    public GestionUsuarios(RepoUsuarios repoUsuarios) {
        this.repoUsuarios = repoUsuarios;
    }

    public void mostrarMenu() {
        final String MENU_USUARIO = "Acciones:\n" +
                "1) Ver Usuarios\n" +
                "2) Crear Usuario\n" +
                "3) Editar Usuario\n" +
                "4) Eliminar Usuario\n" +
                "5) Menu Principal";

        if (!repoUsuarios.cargarFicheroUsuarios()) {
            System.out.println("Error: Fallo al cargar usuarios.");
            return;
        }

        // Bucle de selección de acciones de menú.
        int opcion = -1;
        while (opcion != 5) {
            try {
                opcion = Integer.parseInt(preguntar("\n" + MENU_USUARIO + "\n\nOpción: "));
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    verUsuarios();
                    break;
                case 2:
                    cearUsuario();
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

    private Usuario buscarPorDni(String dni) {
        for (Usuario u : repoUsuarios.getLista()) {
            System.out.println(u.getDni());
            if (u.getDni().equalsIgnoreCase(dni)) {
                return u;
            }
        }
        return null;
    }

    private void verUsuarios() {
        System.out.println("Resultados:");
        for (Usuario u : repoUsuarios.getLista()) {
            System.out.println(" - " + u);
        }
    }

    private void cearUsuario() {
        // pedir información de usuario
        String dni = preguntar("DNI: ");

        Usuario usuarioExistente = buscarPorDni(dni);
        if (usuarioExistente != null) {
            System.out.println("Error: El usuario ya existe.");
            return;
        }

        String nombre = preguntar("Nombre: ");

        int edad;
        try {
            edad = Integer.parseInt(preguntar("Edad: "));
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        // añadir a memoria
        Usuario usuarioNuevo = new Usuario(edad, dni, nombre);
        usuarioNuevo.setUsuarioId(repoUsuarios.getLastId() + 1);
        repoUsuarios.add(usuarioNuevo);

        // volcar memoria a archivo
        if (repoUsuarios.guardarFicheroUsuarios()) {
            System.out.println("Usuario creado correctamente.");
        }
    }

    private void editarUsuario() {
        // pedir información de usuario
        String dni = preguntar("DNI: ");

        Usuario usuarioExistente = buscarPorDni(dni);
        if (usuarioExistente == null) {
            System.out.println("Error: El usuario no existe.");
            return;
        }

        String nombre = preguntar("Nombre: ");

        int edad;
        try {
            edad = Integer.parseInt(preguntar("Edad: "));
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        // actualizar en memoria
        usuarioExistente.setNombre(nombre);
        usuarioExistente.setEdad(edad);

        // volcar memoria a archivo
        if (repoUsuarios.guardarFicheroUsuarios()) {
            System.out.println("Usuario editado correctamente.");
        }
    }

    private void eliminarUsuario() {
        // pedir información de usuario
        String dni = preguntar("DNI: ");

        Usuario usuarioExistente = buscarPorDni(dni);
        if (usuarioExistente == null) {
            System.out.println("Error: El usuario no existe.");
            return;
        }

        // borrar en memoria
        repoUsuarios.remove(usuarioExistente);

        // volcar memoria a archivo
        if (repoUsuarios.guardarFicheroUsuarios()) {
            System.out.println("Usuario eliminado correctamente.");
        }
    }
}
