package tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

//-- Controlador de errores, muestra un mensaje mas amigable al usuario cuando se produce un error.
@RestControllerAdvice
class ExceptionHandler {
    //-- Método para excepciones generales
    @ExceptionHandler(Exception::class)
    fun generalExceptionHandler(exception: Exception): ResponseEntity<ApiError>{
        val error = ApiError(exception.message)
        return ResponseEntity(error, error.status)
    }

    //-- Excepción para cuando la url introducida no se encuentra.
    @ExceptionHandler(URLNotFoundException::class)
    fun URLNotFoundExceptionHandler(exception: Exception): ResponseEntity<ApiError>{
        val error = ApiError(exception.message, HttpStatus.NOT_FOUND)
        return ResponseEntity(error, error.status)
    }

    //-- Excepción para cuando la url introducida no es correcta.
    @ExceptionHandler(InvalidURLException::class)
    fun InvalidURLExceptionHandler(exception: Exception): ResponseEntity<ApiError>{
        val error = ApiError(exception.message)
        return ResponseEntity(error, error.status)
    }
}