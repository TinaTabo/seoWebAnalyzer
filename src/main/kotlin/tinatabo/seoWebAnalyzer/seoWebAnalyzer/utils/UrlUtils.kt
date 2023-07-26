package tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils

import java.net.MalformedURLException
import java.net.URI
import java.net.URISyntaxException
import java.net.URL
import java.util.regex.Pattern

//-- Función para verificar si una URL es válida, es decir, si la string recibida
//-- tiene el patrón correcto para ser una URL.
fun isValidUrl(url: String): Boolean {
    val pattern = Pattern.compile(
        //-- Expresión regular para verificar si la url recibida es válida.
        """^(https?|ftp)://[^\s/$.?#].[^\s]*$""",
        Pattern.CASE_INSENSITIVE
    )
    val isValid = pattern.matcher(url).matches()
    return isValid
}