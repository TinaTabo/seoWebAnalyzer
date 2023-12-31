package tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils.mapper

import org.springframework.stereotype.Component
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.dto.GetResponseDTO
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.entity.Analysis

@Component
class GetResponseMapper: Mapper<GetResponseDTO, Analysis> {
    override fun toDTO(entity: Analysis): GetResponseDTO {
        return GetResponseDTO(
            id = entity.id_analysis ?: 0,
            url = entity.url,
            createdAt = entity.createdAt
        )
    }

    override fun toEntity(dto: GetResponseDTO): Analysis {
        //-- En el caso del método GET no se va a dar el caso de utilizar este método,
        //-- ya que no nos proporciona datos, solo los pide. Por ello controlamos este
        //-- método con un error.
        throw UnsupportedOperationException("No es necesario convertir GetResponseDTO a Analysis.")
    }
}