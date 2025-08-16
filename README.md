# Bibliura: Un Catálogo de Libros y Autores fuera de este mundo

Este es una aplicación de consola en Java, construida con **Spring Boot** y **JPA**, que permite interactuar con la API de Gutendex para buscar y gestionar un catálogo de libros y autores. La aplicación almacena toda la información en una base de datos relacional para un acceso eficiente.

## Funcionalidades Principales

* **Búsqueda y Registro de Libros**: Permite a los usuarios buscar libros por título a través de la API de Gutendex y guardarlos en la base de datos local.
* **Catálogo de Libros**: Muestra una lista de todos los libros registrados.
* **Catálogo de Autores**: Muestra una lista de autores registrados. Cuando se listan los libros de un autor, todos los títulos se muestran bajo una única entrada de autor para evitar duplicados.
* **Búsqueda por Idioma**: Permite filtrar y mostrar libros por su idioma.
* **Autores Vivos por Año**: Permite buscar y listar autores que estuvieron vivos en un año específico.

---

## Estructura del Proyecto

El proyecto sigue una arquitectura organizada en capas:

* `model`: Contiene las **entidades** (`Autor.java`, `Libro.java`, `Idioma.java`) que se mapean a la base de datos, además de los `records` que modelan la respuesta de la API (`DatosLibro.java`, `DatosAutor.java`).
* `repository`: Incluye las interfaces de los **repositorios** (`LibroRepository.java`, `AutorRepository.java`) que extienden `JpaRepository` para la capa de acceso a datos.
* `principal`: Contiene la lógica principal de la aplicación en la clase `Principal.java`, que gestiona el menú y la interacción con el usuario.
* `service`: Proporciona la lógica de negocio, como el consumo de la API (`ConsumoAPI.java`) y la conversión de JSON a objetos Java (`ConvierteDatos.java`).

---

## Relaciones y Mapeos

### **Libro y Autor**
Se estableció una relación **`ManyToOne`** de `Libro` a `Autor`. Esto permite que varios libros sean asociados a un único autor. Para evitar duplicaciones de autores en la base de datos, la aplicación primero verifica si el autor ya existe antes de registrar un nuevo libro.

### **Libro e Idioma**
Se utilizó una relación **`@ElementCollection`** en la entidad `Libro` para almacenar una lista de idiomas. El idioma se representa como un `enum` (`Idioma.java`), que mapea los códigos de idioma de la API a su nombre completo, lo que facilita la búsqueda.

---

## Tecnologías Utilizadas

* **Java 21**: Lenguaje de programación.
* **Spring Boot**: Framework que simplifica el desarrollo de aplicaciones.
* **Spring Data JPA**: Abstrae la capa de persistencia con el uso de repositorios.
* **Hibernate**: Implementación de JPA que realiza el mapeo objeto-relacional.
* **Maven**: Herramienta de gestión de dependencias y construcción.
* **H2 Database**: Base de datos en memoria utilizada para el desarrollo.
* **Jackson**: Utilizada para el manejo y mapeo de datos JSON.

---

**Espero les guste :D**
