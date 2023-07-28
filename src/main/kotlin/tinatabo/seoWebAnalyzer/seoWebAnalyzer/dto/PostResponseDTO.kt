package tinatabo.seoWebAnalyzer.seoWebAnalyzer.dto

import java.time.LocalDateTime

//-- Formato de respuesta de la petición POST.
data class PostResponseDTO(
    val id: Int?,
    val url: String,
    val title: String,
    val description: String,
    val keywords: List<String>,
    val titles: Map<String, Int>,
    val html5: Boolean,
    val images: Int,
    val createdAt: LocalDateTime,
    val isNew: Boolean = true
)
