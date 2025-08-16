package com.aluradesafio.bibliura.principal;

import com.aluradesafio.bibliura.model.*;
import com.aluradesafio.bibliura.repository.AutorRepository;
import com.aluradesafio.bibliura.repository.LibroRepository;
import com.aluradesafio.bibliura.service.ConsumoAPI;
import com.aluradesafio.bibliura.service.ConvierteDatos;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private ObjectMapper objectMapper = new ObjectMapper();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;


    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar y guardar libros por titulo
                    2 - Listar libros guardados
                    3 - Listar autores guardados
                    4 - Listar autores por año
                    5 - Listar libros por idioma
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibrosWeb();
                    break;
                case 2:
                    listarLibrosGuardados();
                    break;
                case 3:
                    listarAutoresGuardados();
                    break;
                case 4:
                    listarAutoresPorAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
                Libros de idioma a buscar:
                es - Español
                en - Inglés
                pt - Portugués
                fr - Francés
                de - Alemán
                """);
        var codigoIdioma = teclado.nextLine().toLowerCase();
        try {
            Idioma idioma = Idioma.fromCodigo(codigoIdioma);
            List<Libro> librosPorIdioma = libroRepository.findByIdiomasIn(List.of(idioma));
            if (librosPorIdioma.isEmpty()) {
                System.out.println("No se encontraron libros en ese idioma.");
            } else {
                System.out.println("--Libros en " + idioma.getNombreCompleto() + " -----");
                librosPorIdioma.forEach(System.out::println);
                System.out.println("----------------------------------------");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarAutoresPorAnio() {
        System.out.println("Escribe el año de busqueda:");
        var anio = teclado.nextInt();
        teclado.nextLine();
        List<Autor> autoresVivos = autorRepository.findAuthorsAliveInYear(anio);
        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año");
        } else {
            System.out.println("-- Autores encontrados --");
            for (Autor autor : autoresVivos) {
                System.out.println("----------");
                System.out.println(autor);
            }
            System.out.println("----------------------------------------------");
        }
    }

    private void listarAutoresGuardados() {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            System.out.println("----- Autores Registrados -----");
            for (Autor autor : autores) {
                System.out.println("----------");
                System.out.println(autor);
            }
            System.out.println("------------------------------");
        }
    }

    private void listarLibrosGuardados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros guardados.");
        } else {
            System.out.println("--Libros guardados:--");
            for (Libro libro : libros) {
                System.out.println("----------");
                System.out.println(libro);
            }
        }
        System.out.println("------------------------------");
    }

    private void buscarLibrosWeb() {
        DatosLibro datosLibro = getDatosLibro();
        if (datosLibro != null && datosLibro.titulo() != null) {
            DatosAutor datosAutor = datosLibro.autores().isEmpty() ? null : datosLibro.autores().get(0);
            Autor autor = null;
            if (datosAutor != null) {
                Optional<Autor> autorExistente = autorRepository.findByNombre(datosAutor.nombre());
                if (autorExistente.isPresent()) {
                    autor = autorExistente.get();
                    System.out.println("Autor encontrado: " + autor.getNombre());
                } else {
                    autor = new Autor(datosAutor.nombre(), datosAutor.fechaNacimiento(), datosAutor.fechaFallecimiento());
                    autor = autorRepository.save(autor);
                    System.out.println("Nuevo autor: " + autor.getNombre());
                }
            }
            Libro libro = new Libro(datosLibro);
            libro.setAutor(autor);
            try {
                libroRepository.save(libro);
                System.out.println("Libro guardado con éxito:");
                System.out.println(libro);
            } catch (Exception e) {
                System.out.println("Error al guardar el libro");
            }
        } else {
            System.out.println("Libro no encontrado");
        }
    }

    private DatosLibro getDatosLibro() {
        System.out.println("Escribe el nombre del Libro que deseas buscar:");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "%20"));
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode resultsNode = rootNode.get("results");
            if (resultsNode != null && resultsNode.isArray() && resultsNode.size() > 0) {
                JsonNode firstBookNode = resultsNode.get(0);
                return objectMapper.treeToValue(firstBookNode, DatosLibro.class);
            }
        } catch (Exception e) {
            System.err.println("Error JSON");
        }
        return null;
    }
}