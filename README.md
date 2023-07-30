# SEOWebAnalyzer API REST
Este repositorio contiene el proyecto API REST de la app web SEOWebAnalyzer, la cual forma parte de una prueba técnica.  

## Descripción General
La API ha sido diseñada para realizar análisis SEO de URLs específicas. Para las pruebas se han utilizado las siguientes:  
- http://example.com
- https://www.wikipedia.org
- https://www.bbc.com
- https://www.rtve.es
- http://www.urjc.es
- https://stackoverflow.com/
- https://www.asos.com/es/
- https://www.amazon.es
- https://refactoring.guru/es/design-patterns
- https://kotlinlang.org/
- https://tailwindcss.com/
- https://www.dafont.com/es/
- https://www.atresmedia.com/
- https://angular.io/
- http://www.iemoji.com/#?category=animals-nature&version=36&theme=appl&skintone=default
- https://getbootstrap.com/
- https://mouredev.com/
- https://midu.dev/
- https://www.w3schools.com/
- https://simlish4.com/
- https://www.youtube.com/
- https://www.getmanfred.com/developer-career-report
- https://www.deepl.com/translator
- https://www.stackoverflow.com

Se ha programado en Kotlin utilizando el framework de Spring Boot.  
A continuación se detalla la funcionalidad de los distintos archivos que componen esta API REST.

### Controller: AnalysisController.kt  
Esta clase controladora es responsable de manejar las solicitudes HTTP. Se han definido las siguientes rutas:  
- **POST /analisis**: Realiza un análisis SEO de la URL proporcionada. Primero, verifica si la URL ya ha sido analizada, devolviendo el análisis existente. Si no, realiza un nuevo análisis, lo guarda en la base de datos y lo envía al usuario.
- **GET /analisis**: Recupera todos los análisis SEO realizados hasta el momento, mostrando por defecto las 15 primeras entradas si no se especifica un límite.
- **DELETE /analisis/{id}**: Elimina un análisis SEO específico basándose en su ID.

### DTOs: DeleteResponseDTO.kt, GetResponseDTO.kt, PostResponseDTO.kt  
Estas clases son objetos de transferencia de datos (DTO) que definen cómo se presentarán los datos en las respuestas a las solicitudes HTTP.
- **DeleteResponseDTO**: se utiliza para proporcionar información cuando se elimina un análisis.
- **GetResponseDTO**: se utiliza para proporcionar información de los análisis recuperados.
- **PostResponseDTO**: se utiliza para proporcionar información del análisis realizado.

### Entidades: Analysis.kt, Keywords.kt, TitleCount.kt
Estas clases son entidades JPA que representan las tablas en la base de datos:   
- **Analysis**: Representa un análisis SEO específico, y tiene una relación 'One to Many' con las entidades Keywords y TitleCount.
- **Keywords**: Representa las palabras clave extraídas durante un análisis SEO.
- **TitleCount**: Representa la cantidad de ciertos tipos de títulos encontrados durante un análisis SEO.

### Model: AnalysisRequest.kt
Esta clase es un objeto de modelo que se utiliza para capturar la URL proporcionada en la solicitud POST para realizar un análisis SEO.

### Repositorio: AnalysisRepository.kt
Este repositorio proporciona operaciones CRUD (Crear, Leer, Actualizar, Borrar) para la entidad Analysis.

### Servicio: AnalysisService.kt y AnalysisServiceImpl.kt
Estas clases definen y proporcionan implementación para las operaciones del servicio:  
- **makeAnalysis(url: String)**: Realiza un análisis SEO de la URL proporcionada.
- **getAllAnalysis(limit: Int)**: Recupera todos los análisis SEO realizados hasta el momento, hasta el límite especificado.
- **deleteAnalysis(id: Int)**: Elimina un análisis SEO específico basándose en su ID.

### Utils
#### Excepciones: Carpeta exceptions
En esta carpeta se encuentran todas las excepciones personalizadas y el controlador de excepciones para la aplicación:  
- **ApiError.kt**:  Define el error de la API que se enviará al cliente si ocurre algún error durante el procesamiento de una solicitud. Contiene un mensaje, un código de estado HTTP y un código numérico correspondiente al estado HTTP.
- **ExceptionHandler.kt**: Esta es una clase de asesoramiento de controlador global que maneja todas las excepciones no manejadas. Cada método en esta clase maneja un tipo de excepción y devuelve un objeto ApiError envuelto en ResponseEntity con el estado HTTP correspondiente.
- **InvalidURLException.kt**: Una excepción personalizada que se lanza si se proporciona una URL no válida.
- **URLNotFoundException.kt**: Otra excepción personalizada que se lanza si la URL proporcionada no se encuentra.

#### Mappers: Carpeta mapper
Los mapeadores son responsables de convertir objetos de entidad en DTOs y viceversa. Los mappers se utilizan para separar las entidades de la base de datos del resto de la aplicación.  
- **GetResponseMapper.kt**: Convierte la entidad Analysis a GetResponseDTO.
- **Mapper.kt**: Interfaz general para todos los mappers.
- **PostResponseMapper.kt**: Convierte la entidad Analysis a PostResponseDTO y viceversa.
  
#### UrlUtils.kt
Proporciona una función para verificar si una URL dada es válida.  

### WebAnalyzer
La clase WebAnalyzer realiza el análisis SEO real de una URL dada. Se conecta a la URL, extrae los datos requeridos y los guarda en un objeto Analysis que se puede guardar en la base de datos, utilizando para ello la biblioteca Jsoup.

### SeoWebAnalyzerApplication.kt
Este es el archivo principal de la aplicación. Inicia la aplicación y es el punto de entrada de la API.  

### BBDD - MySQL
Se utiliza MySQL y Hibernate con la estrategia de generación de esquema update, que actualiza automáticamente el esquema de la base de datos cuando cambia el modelo de la aplicación.  

  
En resumen, esta API toma una URL, realiza un análisis SEO en el contenido de esa URL y guarda los resultados en una base de datos MySQL. Los resultados luego se pueden recuperar a través de la API y presentarse al cliente. Las diversas clases y archivos soportan esta funcionalidad.
