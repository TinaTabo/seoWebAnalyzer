package tinatabo.seoWebAnalyzer.seoWebAnalyzer.dto

import org.springframework.http.HttpStatus

data class DeleteResponseDTO(
    val message: String,
    val status: HttpStatus,
    val code: Int
)
