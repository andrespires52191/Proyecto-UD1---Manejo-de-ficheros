package org.example;

import org.example.modelos.Libro;
import org.example.repos.RepoLibros;

import static org.example.EntradaDatos.preguntar;

public class GestionLibros {
    private final RepoLibros repoLibros;

    public GestionLibros(RepoLibros repoLibros) {
        this.repoLibros = repoLibros;
    }

    public void mostrarMenu() {
        final String MENU_LIBROS = "Acciones:\n" +
                "1) Ver Libros\n" +
                "2) Crear Libro\n" +
                "3) Editar Libro\n" +
                "4) Eliminar Libro\n" +
                "5) Menu Principal";

        if (!repoLibros.cargarFicheroLibros()) {
            System.out.println("Error: Fallo al cargar libros.");
            return;
        }

        // Bucle de selección de acciones de menú.
        int opcion = -1;
        while (opcion != 5) {
            try {
                opcion = Integer.parseInt(preguntar(MENU_LIBROS + "\n\nOpción: "));
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    verLibros();
                    break;
                case 2:
                    cearLibro();
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

    private Libro preguntarDatosLibro() {
        String titulo = preguntar("Titulo: ");
        String autor = preguntar("Autor: ");
        int anyo = 0;
        try {
            anyo = Integer.parseInt(EntradaDatos.preguntar("Año: "));
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
        return new Libro(titulo, autor, anyo);
    }

    private Libro buscarLibro(Libro libro) {
        for (Libro l : repoLibros.getLista()) {
            // buscar primera coincidencia
            if (l.getTitulo().equals(libro.getTitulo())
                    && l.getAutor().equals(libro.getAutor())
                    && l.getAnyo() == libro.getAnyo()
            ) {
                return l;
            }
        }
        return null;
    }

    private void verLibros() {
        System.out.println("Resultados:");
        for (Libro l : repoLibros.getLista()) {
            System.out.println(l);
        }
        System.out.println();
    }

    private void cearLibro() {
        // pedir datos para crear
        Libro libroNuevo = preguntarDatosLibro();
        if (libroNuevo == null) {
            System.out.println("Error: Fallo al conseguir los datos");
            return;
        }

        // revisar si existía anteriormente
        Libro libroExistente = buscarLibro(libroNuevo);
        if (libroExistente != null) {
            System.out.println("El libro ya existe.");
            return;
        }

        // añadir a memoria
        libroNuevo.setLibroId(repoLibros.getLastId() + 1);
        repoLibros.add(libroNuevo);

        // volcar memoria a archivo
        if (repoLibros.guardarFicheroLibros()) {
            System.out.println("Libro creado correctamente.");
        }
    }

    private void editarLibro() {
        // pedir datos para la búsqueda
        System.out.println("Introduce los valores antiguos.");
        Libro libroViejo = preguntarDatosLibro();
        if (libroViejo == null) {
            System.out.println("Error: Fallo al conseguir los datos");
            return;
        }

        // buscar libro en memoria
        Libro libroExistente = buscarLibro(libroViejo);
        if (libroExistente == null) {
            System.out.println("El libro no existe.");
            return;
        }

        // pedir datos finales
        System.out.println("Introduce los valores actualizados.");
        Libro libroNuevo = preguntarDatosLibro();
        if (libroNuevo == null) {
            System.out.println("Error: Fallo al conseguir los datos");
            return;
        }

        // actualizar en memoria
        libroExistente.setAnyo(libroNuevo.getAnyo());
        libroExistente.setAutor(libroNuevo.getAutor());
        libroExistente.setTitulo(libroNuevo.getTitulo());

        // volcar memoria a archivo
        if (repoLibros.guardarFicheroLibros()) {
            System.out.println("Libro editado correctamente.");
        }
    }

    private void eliminarLibro() {
        // pedir datos para la búsqueda
        Libro libroViejo = preguntarDatosLibro();
        if (libroViejo == null) {
            System.out.println("Error: Fallo al conseguir los datos");
            return;
        }

        // buscar libro en memoria
        Libro libroExistente = buscarLibro(libroViejo);
        if (libroExistente == null) {
            System.out.println("El libro no existe.");
            return;
        }

        // borrar en memoria
        repoLibros.remove(libroExistente);

        // volcar memoria a archivo
        if (repoLibros.guardarFicheroLibros()) {
            System.out.println("Libro eliminado correctamente.");
        }
    }
}
