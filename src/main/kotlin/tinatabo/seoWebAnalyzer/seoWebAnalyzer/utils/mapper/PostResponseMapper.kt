package tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils.mapper

import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.dto.PostResponseDTO
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.entity.Analysis
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.entity.Keywords
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.entity.TitleCount
import java.time.LocalDateTime

@Component
class PostResponseMapper: Mapper<PostResponseDTO, Analysis> {
    //-- DTO: Formato para enviar los datos de la entidad desde la API al Front
    override fun toDTO(entity: Analysis): PostResponseDTO {
        return PostResponseDTO(
            id = entity.id ?: 0,
            url = entity.url,
            title = entity.title,
            description = entity.description,
            keywords = entity.keywords.map { it.keyword },
            titles = entity.titles.associate { it.titleType to it.count },
            html5 = entity.html5,
            images = entity.images,
            createdAt = entity.createdAt.toString()
        )
    }

    //-- Entity: Formato para recoger los datos de la DTO y pasarlos a entidad para guardar en la BBDD.
    override fun toEntity(dto: PostResponseDTO): Analysis {
        val analysis = Analysis(
            url = dto.url,
            title = dto.title,
            description = dto.description,
            html5 = dto.html5,
            images = dto.images,
            createdAt = LocalDateTime.parse(dto.createdAt)
        )

        //-- Estos elementos no se pueden agregar directamente en el return ya que para asignalos
        //-- debe haberse creado primero una instancia de la clase 'Analysis'.
        analysis.keywords = dto.keywords.map { Keywords(keyword = it, analysis = analysis)}.toMutableList()
        analysis.titles = dto.titles.entries.map { TitleCount(titleType = it.key, count = it.value, analysis = analysis) }.toMutableList()

        return analysis
    }
}