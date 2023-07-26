package tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils.exceptions

//-- Excepción que salta cuando la URL no es valida. Se verifica mediante una expresión regular.
data class InvalidURLException(override val message: String?): Exception(message)
