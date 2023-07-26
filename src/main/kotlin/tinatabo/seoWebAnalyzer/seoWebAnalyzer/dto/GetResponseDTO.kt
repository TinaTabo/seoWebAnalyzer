package tinatabo.seoWebAnalyzer.seoWebAnalyzer.dto

//-- Formato de respuesta de la petición GET.
data class GetResponseDTO(
    val id: Int,
    val url: String,
    val createdAt:String
)
