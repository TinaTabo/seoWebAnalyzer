package tinatabo.seoWebAnalyzer.seoWebAnalyzer.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.dto.GetResponseDTO
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.dto.PostResponseDTO
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.repository.AnalysisRepository
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils.mapper.GetResponseMapper
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils.mapper.PostResponseMapper
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.webAnalyzer.WebAnalyzer

@Service
class AnalysisServiceImpl(
    //-- @Autowired: sirve para inyectar automaticamente los objetos de las dependencias.
    @Autowired private val analysisRepository: AnalysisRepository,
    @Autowired private val postResponseMapper: PostResponseMapper,
    @Autowired private val getResponseMapper: GetResponseMapper,
    @Autowired private val webAnalyzer: WebAnalyzer
) : AnalysisService {
    override fun makeAnalysis(url: String): PostResponseDTO {
        TODO("Not yet implemented")
        //-- Hacer Análisis de la url y contruir el objeto Analysis.

        //-- Guardar el análisis en la BBDD.

        //-- Convertir la entidad guardada a PostResponseDTO y devolverla al usuario.
    }

    override fun getAnalysis(url: String): PostResponseDTO? {
        TODO("Not yet implemented")
        //-- Buscar en la base de datos el analisis por la url.

        //-- Si el análisis no existe hacer nuevo analisis, llamando al método makeAnalysis

        //-- si el análisis existe, convertir el análisis encontrado a PostResponseDTO y devolverlo.
    }

    override fun getAllAnalysis(limit: Int): List<GetResponseDTO> {
        TODO("Not yet implemented")
        //-- Buscar en la BBDD todos los análisis hasta el limite especificado

        //-- Convertir cada análisis encontrado a GetResponseDTO y devolver la lista con todos.
    }

    override fun deleteAnalysis(id: Long): Boolean {
        TODO("Not yet implemented")
        //-- Buscar analisis por id

        //-- Controlar si el análisis no existe.
        //-- if existe -> lo elimino -> return true
        //-- if not existe -> return false
    }
}