package tinatabo.seoWebAnalyzer.seoWebAnalyzer.webAnalyzer

import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.entity.Analysis
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.entity.TitleCount
import java.time.LocalDateTime

class WebAnalyzerTest {

    @Test
    fun testAnalyze() {
        val url = "http://example.com"

        //-- Simular un documento de prueba.
        val connection = Mockito.mock(Connection::class.java)
        val document = Mockito.mock(Document::class.java)
        Mockito.`when`(connection.get()).thenReturn(document)
        Mockito.`when`(Jsoup.connect(url)).thenReturn(connection)

        //-- Simular datos del analisis de la url.
        //-- Simular <title>
        Mockito.`when`(document.title()).thenReturn("My Test Title")
        //-- Simular <meta type=”description”>
        val descriptionElements = Mockito.mock(Elements::class.java)
        Mockito.`when`(descriptionElements.attr("content")).thenReturn("My Test Description")
        Mockito.`when`(document.select("meta[name=description]")).thenReturn(descriptionElements)
        //-- Simular <meta type=”keywords”>
        val keywordElements = Mockito.mock(Elements::class.java)
        Mockito.`when`(keywordElements.attr("content")).thenReturn("keyword1, keyword2")
        Mockito.`when`(document.select("meta[name=keywords]")).thenReturn(keywordElements)
        //-- Simular cabeceras: <h1>, <h2>...
        for (i in 1..6) {
            val headerTags = Mockito.mock(Elements::class.java)
            Mockito.`when`(headerTags.size).thenReturn(i) // Puedes ajustar este valor según tus necesidades
            Mockito.`when`(document.select("h$i")).thenReturn(headerTags)
        }
        //-- Simular uso de HTML5: <header> y <footer>
        val html5Tags = Mockito.mock(Elements::class.java)
        Mockito.`when`(html5Tags.isNotEmpty()).thenReturn(true) // Simula que hay etiquetas de HTML5
        Mockito.`when`(document.select("header, footer")).thenReturn(html5Tags)
        //-- Simular Nº de imágenes
        val imageTags = Mockito.mock(Elements::class.java)
        Mockito.`when`(imageTags.size).thenReturn(5) // Puedes ajustar este valor según tus necesidades
        Mockito.`when`(document.select("img")).thenReturn(imageTags)


        //-- Crear instancia de WebAnalyzer y llamar al método analyze
        val webAnalyzer = WebAnalyzer()
        val analysis = webAnalyzer.analyze(url)

        val dummyAnalysis = Analysis(
            url = "",
            title = "",
            description = "",
            html5 = false,
            images = 0,
            createdAt = LocalDateTime.now()
        )

        val expectedTitles = listOf(
            TitleCount(analysis = dummyAnalysis, titleType = "h1", count = 1),
            TitleCount(analysis = dummyAnalysis, titleType = "h2", count = 2),
            TitleCount(analysis = dummyAnalysis, titleType = "h3", count = 3),
            TitleCount(analysis = dummyAnalysis, titleType = "h4", count = 4),
            TitleCount(analysis = dummyAnalysis, titleType = "h5", count = 5),
            TitleCount(analysis = dummyAnalysis, titleType = "h6", count = 6)
        )

        //.. Verificar el resultado
        assert(analysis.title == "My Test Title")
        assert(analysis.description == "My Test Description")
        assert(analysis.keywords.map { it.keyword } == listOf("keyword1", "keyword2"))
        assert(analysis.titles.map { it.titleType to it.count } == expectedTitles.map { it.titleType to it.count }) // compara las listas
        assert(analysis.html5)
        assert(analysis.images == 5)
    }
}
