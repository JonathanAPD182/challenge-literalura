package com.aluracursos.challenge_literalura.principal;

import com.aluracursos.challenge_literalura.models.*;
import com.aluracursos.challenge_literalura.repositories.IAutorRepositorio;
import com.aluracursos.challenge_literalura.repositories.ILibroRepositorio;
import com.aluracursos.challenge_literalura.services.ConsumoAPI;
import com.aluracursos.challenge_literalura.services.ConvierteDatos;

import java.util.*;

public class Principal {
    private Scanner teclado;
    private ConsumoAPI consumoApi;
    private ConvierteDatos conversor;
    private static final String URL_BASE = "https://gutendex.com/books/";
    private Optional<DatosLibro> libroBuscado;
    private ILibroRepositorio repositorioLibro;
    private IAutorRepositorio repositorioAutor;
    private List<Libro> listaDatosLibros;
    private List<Autor> listaDatosAutores;

    public Principal(ILibroRepositorio repositorioLibro, IAutorRepositorio repositorioAutor) {
        this.teclado = new Scanner(System.in);
        this.consumoApi = new ConsumoAPI();
        this.conversor = new ConvierteDatos();
        this.repositorioLibro = repositorioLibro;
        this.repositorioAutor = repositorioAutor;
    }

    public void muestraElMenu() {
        int opcion = -1;

        while(opcion != 0) {
            String menu = "     1 - Buscar Libro por titulo\n     2 - Listar libros registrados\n     3 - Listar autores registrados\n     4 - Listar autores vivos en un determinado año\n     5 - Listar libros por idioma\n     0 - Salir\n";
            System.out.println(menu);
            try {
                opcion = this.teclado.nextInt();
                this.teclado.nextLine();
                switch (opcion) {
                    case 0:
                        System.out.println("Saliendo del programa");
                        break;
                    case 1:
                        buscarLibroPorTitulo();
                        break;
                    case 2:
                        listarLibrosRegistrados();
                        break;
                    case 3:
                        listarAutoresRegistrados();
                        break;
                    case 4:
                        listarAutoresVivosSegunAnio();
                        break;
                    case 5:
                        listarLibrosPorIdioma();
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            }catch (InputMismatchException Iex){
                System.out.println("opción no valida para el parametro digitado!!! Por favor selecciona una opción de las que se presentan a continuación." );
                opcion = -1;
                this.teclado.nextLine(); // Limpiar el buffer del scanner
            }
        }
    }


    private List<Libro> getDatosLibros() {
        return repositorioLibro.findAll();
    }

    private void buscarLibroPorTitulo() {

        System.out.println("Escribe el nombre del libro que deseas buscar");
        String nombreLibro = this.teclado.nextLine();
        this.listaDatosLibros = this.getDatosLibros();
        boolean encontrado = false;
        Iterator libroIterator = this.listaDatosLibros.iterator();

        while(libroIterator.hasNext()) {
            Libro libro = (Libro)libroIterator.next();
            if (libro.getTitulo().toLowerCase().contains(nombreLibro.toLowerCase())) {
                System.out.println("Libro encontrado en la base de datos local: ");
                System.out.println(libro.toString());
                encontrado = true;
            }
        }

        if (!encontrado) {
            try {
                String json = this.consumoApi.obtenerDatos("https://gutendex.com/books/?search=" + nombreLibro.replace(" ", "%20"));
                Datos datos = this.conversor.obtenerDatos(json, Datos.class);
                //System.out.println(datos);
                DatosLibro libro1 = datos.resultados().get(0);
                System.out.println(libro1);

                if (libro1 != null){
                    Libro libroNuevo = new Libro(libro1);
                    repositorioLibro.save(libroNuevo);
                    System.out.println(libroNuevo);
                }else {
                    System.out.println("No se encontraron resultados para nombre de libro: "+nombreLibro);
                }
            } catch (IndexOutOfBoundsException v) {
                //IndexOutOfBoundsException e = v;
                System.out.println("Libro no encontrado en el API Gutendex!!! Por favor elige otra opción! \n\n");
            }
        }

    }

    private void listarLibrosRegistrados() {
        this.listaDatosLibros = this.getDatosLibros();
        listaDatosLibros.forEach(System.out::println);
    }

    private void listarAutoresRegistrados(){
        listaDatosAutores = repositorioAutor.findAll();
        listaDatosAutores.forEach(System.out::println);
    }

    private void listarAutoresVivosSegunAnio() {
        System.out.println("indica el año del que quieres consultar los autores que siguen vivos en ese año");
        var anioAutor = teclado.nextInt();
        List<Autor> autor = repositorioAutor.findAutoresVivosPorFecha(anioAutor);
        if (autor.isEmpty()){
            System.out.println("No se encontraron autores vivos para esa fecha");
        }else {
            autor.forEach(System.out::println);
        }
    }

    private void listarLibrosPorIdioma() {
        listaDatosLibros = getDatosLibros();
        System.out.println(""" 
                ingrese el nombre o parte del nombre de uno de los siguiente idiomas en el que requiere consultar los libros disponibles:
                ------------------------------------------
                |       valor       |       Idioma       |
                ------------------------------------------
                |        es         -     'Español'      |
                |        en         -     'Inglés'       |
                |        fr         -     'Fránces'      |
                |        pt         -     'Portugués'    |
                |        fi         -     'Finés'        |
                |        nl         -     'Neerlandés'   |
                ------------------------------------------
                """);
        String idioma = teclado.nextLine();
        List<Libro> librosPorIdioma = repositorioLibro.findByIdiomas(idioma);
        //System.out.println(librosPorIdioma.size());

        if(librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron resultados para el idioma: " + idioma + ".\n");
        } else {
            System.out.println(librosPorIdioma.size());
            librosPorIdioma.forEach(System.out::println);
        }
    }
}

