package org.example;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.example.modelos.Libro;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.example.EntradaDatos.preguntar;

public class GestionLibros {
    static {
        // Force XStream to use PureJavaReflectionProvider globally
        System.setProperty("xstream.converters.reflection.provider", "com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider");
    }

    static XStream xstream = new XStream();
    static File fichero = new File("ficheros/libros.xml");
    static ListaLibros libros = new ListaLibros();
    
    public GestionLibros() {
        final String MENU_LIBROS = "Acciones:\n" +
                "1) Ver Libros\n" +
                "2) Crear Libro\n" +
                "3) Editar Libro\n" +
                "4) Eliminar Libro\n" +
                "5) Menu Principal";

        if (!cargarFicheroLibros()) {
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

    private boolean cargarFicheroLibros() {
        if (!fichero.exists()) {
            // nada que cargar
            return true;
        }

        try {
            // cambiar de nombre a las etiquetas XML
            xstream.alias("ListaLibros", ListaLibros.class);
            xstream.alias("DatosLibro", Libro.class);

            // colección por defecto
            xstream.addImplicitCollection(ListaLibros.class, "lista");

            // cargar fichero a memoria
            // necesita permisos para no dar error
            xstream.addPermission(AnyTypePermission.ANY);
            libros = (ListaLibros) xstream.fromXML(new FileInputStream(fichero));

            // exito
            return true;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // fallo
        return false;
    }

    private boolean guardarFicheroLibros() {
        try {
            // cambiar de nombre a las etiquetas XML
            xstream.alias("ListaLibros", ListaLibros.class);
            xstream.alias("DatosLibro", Libro.class);

            // colección por defecto
            xstream.addImplicitCollection(ListaLibros.class, "lista");

            // guardar
            xstream.toXML(libros, new FileOutputStream(fichero));

            // exito
            return true;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // fallo
        return false;
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
        for (Libro l : libros.getLista()) {
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
        for (Libro l : libros.getLista()) {
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

        // actualizar en memoria
        libros.add(libroNuevo);

        // volcar memoria a archivo
        if (guardarFicheroLibros()) {
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
        if (guardarFicheroLibros()) {
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
        libros.remove(libroExistente);

        // volcar memoria a archivo
        if (guardarFicheroLibros()) {
            System.out.println("Libro eliminado correctamente.");
        }
    }
}
