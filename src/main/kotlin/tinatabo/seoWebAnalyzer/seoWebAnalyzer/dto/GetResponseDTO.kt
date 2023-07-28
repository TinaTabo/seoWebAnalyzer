package tinatabo.seoWebAnalyzer.seoWebAnalyzer.dto
import java.time.LocalDateTime
//-- Formato de respuesta de la petición GET.
data class GetResponseDTO(
    val id: Int,
    val url: String,
    val createdAt: LocalDateTime
)
