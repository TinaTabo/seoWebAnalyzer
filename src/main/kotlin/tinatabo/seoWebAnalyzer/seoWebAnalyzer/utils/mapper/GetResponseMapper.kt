package tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils.mapper

import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.dto.GetResponseDTO
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.entity.Analysis
import java.time.format.DateTimeFormatter

@Component
class GetResponseMapper: Mapper<GetResponseDTO, Analysis> {
    override fun toDTO(entity: Analysis): GetResponseDTO {
        return GetResponseDTO(
            id = entity.id ?: 0,
            url = entity.url,
            createdAt = entity.createdAt.format(DateTimeFormatter.ISO_DATE) //-- ISO_DATE utiliza el formato de la ISO 8601, que se ve asi: 2023-07-24T16:30:00.000Z
        )
    }

    override fun toEntity(dto: GetResponseDTO): Analysis {
        //-- En el caso del método GET no se va a dar el caso de utilizar este método,
        //-- ya que no nos proporciona datos, solo los pide. Por ello controlamos este
        //-- método con un error.
        throw UnsupportedOperationException("No es necesario convertir GetResponseDTO a Analysis.")
    }
}