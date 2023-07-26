package tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils.exceptions

import org.springframework.http.HttpStatus

data class ApiError(
    //-- Si algo va mal, este mensaje será igual a "Something went wrong"
    private val _message: String?,
    private val _status: HttpStatus = HttpStatus.BAD_REQUEST
){
    val message: String
        get() = _message ?: "Something went wrong"

    val status: HttpStatus
        get() = _status

    val code: Int
        get() = status.value()
}
