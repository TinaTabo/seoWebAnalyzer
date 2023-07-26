package tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils.exceptions

import org.springframework.http.HttpStatus

//-- Excepción que salta cuando la URL introducida no se encuentra.
data class URLNotFoundException(override val message: String?): Exception(message)
