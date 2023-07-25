package tinatabo.seoWebAnalyzer.seoWebAnalyzer.webAnalyzer

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.entity.Analysis
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.entity.Keywords
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.entity.TitleCount
import java.time.LocalDateTime

//-- Esta clase implementa las funciones para realizar el análisis SEO de la url.
@Component
class WebAnalyzer {
    //-- Función para realizar el analisis SEO según las especificaciones.
    /*
        1. Extraer el título de la web del tag <title>
        2. Extraer la descripción de la web del tag <meta type=”description”>
        3. Extraer la lista de palabras clave del tag <meta type=”keywords”>
        4. Contar cuántos elementos de tipo título, es decir, <h1>, <h2>... hay (contar cada
           tipo por separado)
        5. Detectar si hace uso de etiquetas de HTML5 como <header> y <footer>
        6. Contar cuantas imágenes contiene la web.
    */
    fun analyze(url: String): Analysis{
        //-- Haciendo uso de la biblioteca Jsoup, se conecta a la url y se obtiene un objeto
        //-- de tipo 'Document' que representa la pg web.
        val document: Document = Jsoup.connect(url).get()

        //-- Se extraen del documento los datos requeridos.
        val title = document.title()
        val description = document.select("meta[type=description]").attr("content")
        val keywords = document.select("meta[type=keywords]").attr("content").split(',')

        val titles: MutableMap<String, Int> = mutableMapOf()
        for(i in 1..6){
            val headersTags = document.select("h$i")
            if (headersTags.size > 0){
                titles["h$i"] = headersTags.size
            }
        }

        //-- Al usar !! le digo al compilador que se que html5 nunca va a ser null
        //-- En el caso de que lo fuese causaria una NullPointerException
        val html5 = document.documentType()!!.publicId().contains("html5",true)
        val images = document.select("img").size

        //-- Creamos una variable de tipo 'Analysis' que nos servirá para guardar el análisis en la BBDD
        val analysis = Analysis(
            url = url,
            title = title,
            description = description,
            html5 = html5,
            images = images,
            createdAt = LocalDateTime.now()
        )
        //-- Con la funcion map{...} se transforman los objetos en una lista. Recibe como parámetro una
        //-- función que aplica a cada elemento de la lista. En este caso, la función crea un nuevo objeto
        //-- de la clase Keywords por cada elemento. Con trim() se eliminan los espacion en blanco al
        //-- principio y al final. Por ultimo se convierte la lista generada por map a una lista mutable.
        //-- Esto finalmente se asigna al campo keywords del objeto analysis.
        analysis.keywords = keywords.map { Keywords(keyword = it.trim(), analysis = analysis) }.toMutableList()
        analysis.titles = titles.entries.map { TitleCount(titleType = it.key, count = it.value, analysis = analysis) }.toMutableList()

        return analysis
    }
}